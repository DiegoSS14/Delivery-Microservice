package com.diego.delivery.delivery.tracking.domain.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(AccessLevel.PRIVATE)
@Getter
public class Item {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

    @Setter(AccessLevel.PACKAGE)
    private Integer quantity;

    @ManyToOne(optional = false)
    @Setter(AccessLevel.PRIVATE)
    private Delivery delivery;

    public static Item brandNew(String name, Integer quantity, Delivery delivery) {
        Item item = new Item();
        item.setId(UUID.randomUUID());
        item.setName(name);
        item.setQuantity(quantity);
        item.setDelivery(delivery);
        return item;
    }
}
