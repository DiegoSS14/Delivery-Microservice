package com.diego.delivery.delivery.tracking.domain.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DeliveryStatusTest {
    
    @Test
    void draft_canChangeToWaitinForCourier() {
        assertTrue(
            DeliveryStatus.DRAFT.canChangeTo(DeliveryStatus.WAITING_FOR_COURIER)
        );
    }

    @Test
    void draft_changeToInTransit() {
        assertTrue(
            DeliveryStatus.WAITING_FOR_COURIER.canChangeTo(DeliveryStatus.IN_TRANSIT)
        );
    }

    @Test
    void draft_canChangeToDelivery() {
        assertTrue(
            DeliveryStatus.IN_TRANSIT.canChangeTo(DeliveryStatus.DELIVERY)
        );
    }

    @Test
    void draft_CanNotChangeToWaitingForCourier() {
        assertFalse(
            DeliveryStatus.DRAFT.canChangeTo(DeliveryStatus.DELIVERY)
        );
    }
}
