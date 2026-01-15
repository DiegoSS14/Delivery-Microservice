package com.diego.delivery.courier.management.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.diego.delivery.courier.management.api.model.CourierInput;
import com.diego.delivery.courier.management.domain.model.Courier;
import com.diego.delivery.courier.management.domain.repository.CourierRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CourierRegistrationService {

    private final CourierRepository courierRepository;

    public Courier create(CourierInput courierInput) {
        Courier courier = Courier.brandNew(courierInput.getName(), courierInput.getPhone());
        return this.courierRepository.saveAndFlush(courier);
    }

    public Courier update(UUID courierId, CourierInput courierInput) {
        Courier courier = this.courierRepository.findById(courierId).orElseThrow();
        courier.setName(courierInput.getName());
        courier.setPhone(courierInput.getPhone());
        return this.courierRepository.saveAndFlush(courier);
    }
}
