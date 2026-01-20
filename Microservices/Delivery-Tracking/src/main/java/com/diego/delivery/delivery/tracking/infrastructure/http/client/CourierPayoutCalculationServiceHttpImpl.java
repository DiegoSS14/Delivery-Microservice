package com.diego.delivery.delivery.tracking.infrastructure.http.client;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.diego.delivery.delivery.tracking.service.CourierPayoutCalculationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {

    
    private final CourierApiClient courierApiClient;

    @Override
    public BigDecimal calculatePayout(Double distanceInKm) {
        CourierPayoutResultModel courierPayoutResultModel = courierApiClient.payoutCalculation(
                new CourierPayoutCalculationInput(distanceInKm));
        return courierPayoutResultModel.getPayoutFee();
    }
}
