package com.diego.delivery.delivery.tracking.service;

import com.diego.delivery.delivery.tracking.domain.model.ContactPoint;

public interface DeliveryTimeEstimationService {
    DeliveryEstimate estimate(ContactPoint sende, ContactPoint receiver);
}
