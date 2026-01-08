package com.diego.delivery.delivery.tracking.service;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diego.delivery.delivery.tracking.api.model.ContactPointInput;
import com.diego.delivery.delivery.tracking.api.model.DeliveryInput;
import com.diego.delivery.delivery.tracking.api.model.ItemInput;
import com.diego.delivery.delivery.tracking.domain.model.ContactPoint;
import com.diego.delivery.delivery.tracking.domain.model.Delivery;
import com.diego.delivery.delivery.tracking.domain.model.Delivery.PreparationDetails;
import com.diego.delivery.delivery.tracking.domain.model.exception.DomainException;
import com.diego.delivery.delivery.tracking.domain.repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {
    
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public Delivery draft(DeliveryInput input) {
        Delivery delivery = Delivery.draft();
        handleDeliveryPreparation(input, delivery);
        return deliveryRepository.saveAndFlush(delivery);
    }

    @Transactional
    public Delivery edit(UUID deliveryId, DeliveryInput input) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
        .orElseThrow(() -> new DomainException());
        delivery.removeItems();
        handleDeliveryPreparation(input, delivery);
        return deliveryRepository.saveAndFlush(delivery);
    }

    private void handleDeliveryPreparation(DeliveryInput input, Delivery delivery) {
        ContactPointInput senderInput = input.getSender();
        ContactPointInput recipientInput = input.getRecipient();

        ContactPoint sender = ContactPoint.builder()
            .phone(senderInput.getPhone())
            .name(senderInput.getName())
            .complement(senderInput.getComplement())
            .number(senderInput.getNumber())
            .zipCode(senderInput.getZipCode())
            .street(senderInput.getStreet())
            .build();

        ContactPoint recipient = ContactPoint.builder()
            .phone(recipientInput.getPhone())
            .name(recipientInput.getName())
            .complement(recipientInput.getComplement())
            .number(recipientInput.getNumber())
            .zipCode(recipientInput.getZipCode())
            .street(recipientInput.getStreet())
            .build();

        Duration expectedDeliveryDateTime = Duration.ofHours(3);
        BigDecimal distanceFee = new BigDecimal("10");

        BigDecimal payOut = new BigDecimal("10");

        PreparationDetails preparationDetails = PreparationDetails.builder()
            .sender(sender)
            .recipient(recipient)
            .expectedDeliveryTime(expectedDeliveryDateTime)
            .courierPayout(payOut)
            .distanceFee(distanceFee)
            .build();

        delivery.editPreparationDetails(preparationDetails);

        for(ItemInput itemInput: input.getItems()) {
            delivery.addItem(itemInput.getName(), itemInput.getQuantity());
        }
    }
}
