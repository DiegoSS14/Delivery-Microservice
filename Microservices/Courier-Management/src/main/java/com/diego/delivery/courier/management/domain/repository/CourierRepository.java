package com.diego.delivery.courier.management.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego.delivery.courier.management.domain.model.Courier;

public interface CourierRepository extends JpaRepository<Courier, UUID>{

    Optional<Courier> findTop1ByOrderByLastFulfilledDeliveryAtAsc();
    Optional<Courier> findByPendingDeliveries_id(UUID deliveryId);
    // public Page<Courier> findAll(Pageable pageable);

}
