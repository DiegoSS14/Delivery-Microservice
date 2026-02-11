package com.diego.delivery.courier.management.infrastructure.events;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryFullfilledIntegrationEvent {
    private OffsetDateTime ocurredAt;
    private UUID deliveryId;
}
