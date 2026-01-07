package com.diego.delivery.courier.management.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego.delivery.courier.management.domain.model.Courier;

public interface CourierRepository extends JpaRepository<Courier, UUID>{
    
}
