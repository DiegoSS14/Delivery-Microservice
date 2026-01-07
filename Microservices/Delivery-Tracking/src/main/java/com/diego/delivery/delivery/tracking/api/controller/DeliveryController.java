package com.diego.delivery.delivery.tracking.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diego.delivery.delivery.tracking.api.model.DeliveryInput;
import com.diego.delivery.delivery.tracking.domain.model.Delivery;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery draft(@RequestBody @Valid DeliveryInput input) {
        System.out.println("DeliveryController.draft()");
        return null;
    }

    @PostMapping("/{deliveryId}")
    public Delivery edit(
        @RequestParam UUID deliveryId, 
        @RequestBody @Valid DeliveryInput input
    ) {
        return null;
    }
}
