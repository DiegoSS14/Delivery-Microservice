package com.diego.delivery.delivery.tracking.api.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.diego.delivery.delivery.tracking.api.model.CourierInput;
import com.diego.delivery.delivery.tracking.api.model.DeliveryInput;
import com.diego.delivery.delivery.tracking.domain.model.Delivery;
import com.diego.delivery.delivery.tracking.domain.repository.DeliveryRepository;
import com.diego.delivery.delivery.tracking.service.DeliveryCheckPointService;
import com.diego.delivery.delivery.tracking.service.DeliveryPreparationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryPreparationService deliuDeliveryPreparationService;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryCheckPointService deliveryCheckPointService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery draft(@RequestBody @Valid DeliveryInput input) {
        System.out.println("DeliveryController.draft()");
        return deliuDeliveryPreparationService.draft(input);
    }

    @PutMapping("/{deliveryId}")
    public Delivery edit(
            @PathVariable UUID deliveryId,
            @RequestBody @Valid DeliveryInput input) {
        return deliuDeliveryPreparationService.edit(deliveryId, input);
    }

    @GetMapping
    public PagedModel<Delivery> findAll(@PageableDefault Pageable pageable) {
        return new PagedModel<>(deliveryRepository.findAll(pageable));
    }

    @GetMapping("/{deliveryId}")
    public Delivery findById(@PathVariable UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{deliveryId}/placement")
    public void place(@PathVariable UUID deliveryId) {
        deliveryCheckPointService.place(deliveryId);
    } 

    @PostMapping("/{deliveryId}/pickups")
    public void pickUp(@PathVariable UUID deliveryId, @RequestBody CourierInput input) {
        deliveryCheckPointService.pickUp(deliveryId, input.getCourierId());
    }

    @PostMapping("/{deliveryId}/completion")
    public void complete(@PathVariable UUID deliveryId) {
        deliveryCheckPointService.complete(deliveryId);
    }
}
