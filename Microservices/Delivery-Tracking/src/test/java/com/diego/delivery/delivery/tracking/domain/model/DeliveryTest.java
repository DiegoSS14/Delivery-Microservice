package com.diego.delivery.delivery.tracking.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    @Test
    public void shouldChangeToPlaced() {
        Delivery delivery = Delivery.draft();
        delivery.editPreparationDetails(createValidPreparationDetails());
        delivery.place();

        assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
        assertNotNull(delivery.getPlacedAt());
    }

    @Test
    public void shouldNotPlace() {
        Delivery delivery = Delivery.draft();
        delivery.editPreparationDetails(createValidPreparationDetails());

        assertNotEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
        assertNull(delivery.getPlacedAt());
    }

    private Delivery.PreparationDetails createValidPreparationDetails() {
        ContactPoint sender = ContactPoint.builder()
                .zipCode("85624-782")
                .street("Brasília")
                .number("20")
                .complement("Sala 657")
                .name("Diego Sousa")
                .phone("(61) 99654-9510")
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .zipCode("12233-654")
                .street("São Paulo")
                .number("100")
                .complement("Casa 60")
                .name("Antônio Oliveira")
                .phone("(61) 95842-6541")
                .build();

        return Delivery.PreparationDetails.builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(new BigDecimal("15.00"))
                .courierPayout(new BigDecimal("5.00"))
                .expectedDeliveryTime(Duration.ofHours(5))
                .build();
    }
}