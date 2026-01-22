package com.diego.delivery.delivery.tracking.infrastructure.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.diego.delivery.delivery.tracking.domain.event.DeliveryFulfilledEvent;
import com.diego.delivery.delivery.tracking.domain.event.DeliveryPickUpEvent;
import com.diego.delivery.delivery.tracking.domain.event.DeliveryPlacedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j // Anotação de logs do Lombok
@RequiredArgsConstructor
public class DeliveryDomainEventHandler {
    
    @EventListener
    public void handle(DeliveryPlacedEvent event) {
        log.info(event.toString());
    }

    @EventListener
    public void handle(DeliveryPickUpEvent event) {
        log.info(event.toString());
    }

    @EventListener
    public void handle(DeliveryFulfilledEvent event) {
        log.info(event.toString());
    }
}
