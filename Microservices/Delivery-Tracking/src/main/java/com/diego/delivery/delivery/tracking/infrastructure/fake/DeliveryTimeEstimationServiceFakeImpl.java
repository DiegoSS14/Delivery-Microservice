package com.diego.delivery.delivery.tracking.infrastructure.fake;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.diego.delivery.delivery.tracking.domain.model.ContactPoint;
import com.diego.delivery.delivery.tracking.service.DeliveryEstimate;
import com.diego.delivery.delivery.tracking.service.DeliveryTimeEstimationService;

@Service
public class DeliveryTimeEstimationServiceFakeImpl implements DeliveryTimeEstimationService {

    @Override
    public DeliveryEstimate estimate(ContactPoint sende, ContactPoint receiver) {
        return new DeliveryEstimate(Duration.ofHours(3), 3.1);
    }
}
