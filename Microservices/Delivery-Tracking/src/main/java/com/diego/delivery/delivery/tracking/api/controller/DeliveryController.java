package com.diego.delivery.delivery.tracking.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diego.delivery.delivery.tracking.api.model.DeliveryInput;
import com.diego.delivery.delivery.tracking.domain.model.Delivery;
import com.diego.delivery.delivery.tracking.service.DeliveryPreparationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    
    private final DeliveryPreparationService deliuDeliveryPreparationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery draft(@RequestBody @Valid DeliveryInput input) {
        System.out.println("DeliveryController.draft()");
        return deliuDeliveryPreparationService.draft(input);
    }

    @PutMapping("/{deliveryId}")
    public Delivery edit(
        @PathVariable UUID deliveryId, 
        @RequestBody @Valid DeliveryInput input
    ) {
        return deliuDeliveryPreparationService.edit(deliveryId, input);
    }
}
