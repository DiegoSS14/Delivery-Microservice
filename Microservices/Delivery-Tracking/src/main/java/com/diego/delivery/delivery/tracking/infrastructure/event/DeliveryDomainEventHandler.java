package com.diego.delivery.delivery.tracking.infrastructure.event;

import static com.diego.delivery.delivery.tracking.infrastructure.kafka.KafkaTopicConfig.deliveryEventsTopicName;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.diego.delivery.delivery.tracking.domain.event.DeliveryFulfilledEvent;
import com.diego.delivery.delivery.tracking.domain.event.DeliveryPickUpEvent;
import com.diego.delivery.delivery.tracking.domain.event.DeliveryPlacedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryDomainEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;

    @EventListener
    public void handle(DeliveryPlacedEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(event,
                event.getDeliveryId().toString(), deliveryEventsTopicName);
    }

    @EventListener
    public void handle(DeliveryPickUpEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(event,
                event.getDeliveryId().toString(), deliveryEventsTopicName);
    }

    @EventListener
    public void handle(DeliveryFulfilledEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(event,
                event.getDeliveryId().toString(), deliveryEventsTopicName);
    }

}
