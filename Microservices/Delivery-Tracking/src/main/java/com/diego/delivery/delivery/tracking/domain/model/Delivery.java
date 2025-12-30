package com.diego.delivery.delivery.tracking.domain.model;

import lombok.*;

import java.math.BigDecimal;
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
    private BigDecimal courierPayment;
    private  BigDecimal totalCost;

    private Integer totalItems;

    private ContactPoint sender;
    private ContactPoint recipient;

    private List<Item> items = new ArrayList<>();

    public static Delivery draft() {
        Delivery delivery = new Delivery();
        delivery.setId(UUID.randomUUID());
        delivery.setStatus(DeliveryStatus.DRAFT);
        delivery.setDistanceFee(BigDecimal.ZERO);
        delivery.setCourierPayment(BigDecimal.ZERO);
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

    public void changeItemQuantity(UUID itemId,Integer quantity) {
        Item item = getItems().stream().filter(i -> i.getId().equals(itemId)).findFirst()
                .orElseThrow();

        item.setQuantity(quantity);
        claculateTotalItems();
    }

    public void removeItems() {
        this.items.clear();
        claculateTotalItems();
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    private void claculateTotalItems() {
        int totalItems = getItems().stream().mapToInt(Item::getQuantity).sum();
        setTotalItems(totalItems);
    }
}
