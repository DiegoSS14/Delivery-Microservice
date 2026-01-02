package com.diego.delivery.delivery.tracking.domain.model;

import com.diego.delivery.delivery.tracking.domain.model.exception.DomainException;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(AccessLevel.PRIVATE)
@Getter
public class Delivery {
    @EqualsAndHashCode.Include
    private UUID id;

    private UUID courierId;

    private DeliveryStatus status;

    private OffsetDateTime placedAt;
    private OffsetDateTime assignedAt;
    private OffsetDateTime expectedDeliveryAt;
    private OffsetDateTime fulfilledAt;

    private BigDecimal distanceFee;
    private BigDecimal courierPayout;
    private BigDecimal totalCost;

    private Integer totalItems;

    private ContactPoint sender;
    private ContactPoint recipient;

    private List<Item> items = new ArrayList<>();

    public static Delivery draft() {
        Delivery delivery = new Delivery();
        delivery.setId(UUID.randomUUID());
        delivery.setStatus(DeliveryStatus.DRAFT);
        delivery.setDistanceFee(BigDecimal.ZERO);
        delivery.setCourierPayout(BigDecimal.ZERO);
        delivery.setTotalCost(BigDecimal.ZERO);
        return delivery;
    }

    public UUID addItem(String name, Integer quantity) {
        Item item = Item.brandNew(name, quantity);
        this.items.add(item);
        claculateTotalItems();
        return item.getId();
    }

    public void removeItem(UUID itemId) {
        this.items.removeIf(item -> item.getId().equals(itemId));
        claculateTotalItems();
    }

    public void changeItemQuantity(UUID itemId, Integer quantity) {
        Item item = getItems().stream().filter(i -> i.getId().equals(itemId)).findFirst()
                .orElseThrow();

        item.setQuantity(quantity);
        claculateTotalItems();
    }

    public void removeItems() {
        this.items.clear();
        claculateTotalItems();
    }

    // Definir detalhes de preparação
    public void editPreparationDetails(PreparationDetails details) {
        verifyIfCanBeEdited();
        setSender(details.getSender());
        setRecipient(details.getRecipient());
        setDistanceFee(details.getDistanceFee());
        setCourierPayout(details.getCourierPayout());

        setExpectedDeliveryAt(OffsetDateTime.now().plus(details.getExpectedDeliveryTime()));
        setTotalCost(getDistanceFee().add(this.getCourierPayout()));
    }

    // Postar item
    public void place() {
        verifyCanBePlaced();
        setStatus(DeliveryStatus.WAITING_FOR_COURIER);
        setPlacedAt(OffsetDateTime.now());
    }

    // Definir entregador
    public void pickUp(UUID courierId) {
        setCourierId(courierId);
        setStatus(DeliveryStatus.IN_TRANSIT);
        setAssignedAt(OffsetDateTime.now());
    }

    // Marcar como entregue
    public void markAsDelivered() {
        this.setStatus(DeliveryStatus.DELIVERY);
        this.setFulfilledAt(OffsetDateTime.now());
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    private void claculateTotalItems() {
        int totalItems = getItems().stream().mapToInt(Item::getQuantity).sum();
        setTotalItems(totalItems);
    }

    private void verifyCanBePlaced() {
        if (!isFilled()) {
            throw new DomainException();
        }
        if (!this.getStatus().equals(DeliveryStatus.DRAFT)) {
            throw new DomainException();
        }
    }

    private boolean isFilled() {
        return this.getSender() != null
                && this.getRecipient() != null
                && this.getTotalCost() != null;
    }

    private void verifyIfCanBeEdited() {
        if (!getStatus().equals(DeliveryStatus.DRAFT)) {
            throw new DomainException();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PreparationDetails {
        private ContactPoint sender;
        private ContactPoint recipient;
        private BigDecimal distanceFee;
        private BigDecimal courierPayout;
        private Duration expectedDeliveryTime;
    }


}
