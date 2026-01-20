package com.diego.delivery.delivery.tracking.infrastructure.http.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class CourierApiClientConfig {

    @Bean
    public RestClient.Builder restClient() {
        return RestClient.builder();
    }
    
    @Bean // Configuração para conexão com o microsserviço externo
    public CourierApiClient courierApiClient(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl("http://localhost:8081").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return proxyFactory.createClient(CourierApiClient.class);
    }
}
