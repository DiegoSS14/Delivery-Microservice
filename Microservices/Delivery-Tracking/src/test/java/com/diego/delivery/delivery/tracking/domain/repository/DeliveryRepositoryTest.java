package com.diego.delivery.delivery.tracking.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.diego.delivery.delivery.tracking.domain.model.ContactPoint;
import com.diego.delivery.delivery.tracking.domain.model.Delivery;

@DataJpaTest // Implementa testes do JPA
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Não cria o banco de dados automáticamente
public class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void shouldPersist() {
        Delivery delivery = Delivery.draft();

        delivery.editPreparationDetails(createValidPreparationDetails());

        delivery.addItem("Batata frita média", 2);
        delivery.addItem("Pizza carne seca", 1);

        deliveryRepository.saveAndFlush(delivery);

        Delivery persistedDelivery = deliveryRepository.findById(delivery.getId()).orElseThrow();

        assertEquals(2, persistedDelivery.getItems().size());
    }

    private Delivery.PreparationDetails createValidPreparationDetails() {
        ContactPoint sender = ContactPoint.builder()
                .zipCode("85624-782")
                .street("Brasília")
                .number("20")
                .complement("Sala 657")
                .name("Diego Sousa")
                .phone("(61) 99654-9510")
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .zipCode("12233-654")
                .street("São Paulo")
                .number("100")
                .complement("Casa 60")
                .name("Antônio Oliveira")
                .phone("(61) 95842-6541")
                .build();

        return Delivery.PreparationDetails.builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(new BigDecimal("15.00"))
                .courierPayout(new BigDecimal("5.00"))
                .expectedDeliveryTime(Duration.ofHours(5))
                .build();
    }
}
