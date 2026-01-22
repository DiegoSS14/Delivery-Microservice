package com.diego.delivery.delivery.tracking.domain.event;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class DeliveryFulfilledEvent {
    private OffsetDateTime occurredAt;
    private UUID deliveryId;
}
