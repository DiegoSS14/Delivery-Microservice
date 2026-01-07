package com.diego.delivery.delivery.tracking.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego.delivery.delivery.tracking.domain.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID>{
    
}
