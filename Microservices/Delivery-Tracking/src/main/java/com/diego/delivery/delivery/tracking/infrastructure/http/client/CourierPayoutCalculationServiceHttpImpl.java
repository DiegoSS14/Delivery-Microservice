package com.diego.delivery.delivery.tracking.infrastructure.http.client;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.diego.delivery.delivery.tracking.infrastructure.http.client.exception.BadGatewayException;
import com.diego.delivery.delivery.tracking.infrastructure.http.client.exception.GatewayTimeOut;
import com.diego.delivery.delivery.tracking.service.CourierPayoutCalculationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {

    
    private final CourierApiClient courierApiClient;

    @Override
    public BigDecimal calculatePayout(Double distanceInKm) {
        try{
            return courierApiClient.payoutCalculation(
                    new CourierPayoutCalculationInput(distanceInKm)).getPayoutFee();
        } catch(ResourceAccessException e) {
            throw new GatewayTimeOut(e);
        } catch(HttpServerErrorException e) {
            throw new BadGatewayException(e);
        }
    }
}
