# Delivery - Projeto de Microsserviços

## 📋 Descrição

Delivery é uma aplicação construída com arquitetura de **microsserviços**. O projeto separa responsabilidades em domínios distintos, cada um operando de forma independente e se comunicando através de mensageria assíncrona.

## 🏗️ Arquitetura

O projeto é composto por **2 microsserviços**:

### 1. **Delivery-Tracking** 
API responsável pelo gerenciamento e rastreamento de entregas.
- Controla o status das entregas (draft, placed, in-transit, delivered, cancelled)
- Gerencia informações de entrega
- Publica eventos de mudança de status via Kafka

### 2. **Courier-Management**
API responsável pelo gerenciamento de entregadores.
- Gerencia informações dos entregadores (couriers)
- Controla disponibilidade e localização
- Integra-se com Delivery-Tracking via eventos Kafka

## 🔄 Comunicação

Os microsserviços se comunicam através de **Apache Kafka** utilizando event-driven architecture, garantindo desacoplamento e escalabilidade.

## 🛠️ Stack Tecnológico

- **Java 21**
- **Spring Boot 4.0.1**
- **Spring Kafka 4.0.1**
- **PostgreSQL**
- **Apache Kafka**
- **Jackson** (serialização JSON)

## 🚀 Como Executar

### Pré-requisitos
- JDK 21
- Maven
- Docker (para Kafka e PostgreSQL)

### Passos
1. Clone o repositório
2. Execute `docker-compose up` na raiz do projeto
3. Em cada microsserviço, execute: `mvn clean install`
4. Inicie ambas as aplicações

## 📌 Desafios Encontrados

### 🥇 Primeiro Grande Desafio: Configuração do Kafka com KafkaTemplate

**Problema Inicial:**
O Bean `KafkaTemplate` não estava sendo encontrado na aplicação. A aplicação não conseguia injetar a dependência, o que indicava que a configuração do Kafka não havia sido definida corretamente.

**Sequência de Eventos:**
Após implementar a classe `AppConfig` para definir o Bean `KafkaTemplate`, ao tentar chamar a rota de **placement** (que envia eventos para o Kafka), a aplicação retornou um **erro 500**. A investigação desse erro revelou uma cascata de problemas sucessivos:

1. **ClassNotFoundException: com.fasterxml.jackson.databind.JavaType** - Dependência Jackson ausente necessária pelo JsonSerializer
2. **InvalidDefinitionException com OffsetDateTime** - Jackson não suportava tipos Java 8 de data/hora nativamente
3. **ProducerProperties incorretas** - Faltava serializer explícito para valores

**Solução:**
- Criar classe `AppConfig` com configuração customizada de `ProducerFactory` e `KafkaTemplate`
- Adicionar dependências: `jackson-databind` e `jackson-datatype-jsr310`
- Configurar serializers (StringSerializer para chaves, JsonSerializer para valores)
- Implementar `ProducerFactory` com propriedades específicas (ACKS, RETRIES, LINGER_MS)

**Aprendizado:**
Este desafio ilustrou a importância de:
- Configuração explícita de Beans para microsserviços
- Gerenciamento de dependências transitivas
- Suporte adequado para tipos modernos de Java (Java 8+ date/time API)
- Serialização correta de objetos em comunicação assíncrona
Delivery/
├── docker-compose.yaml
├── README.md
└── Microservices/
    ├── Delivery-Tracking/
    │   ├── src/
    │   ├── pom.xml
    │   └── ...
    └── Courier-Management/
        ├── src/
        ├── pom.xml
        └── ...
```

## 📝 Notas

Este é um projeto acadêmico desenvolvido como objeto de estudo e para consulta futura sobre arquitetura de microsserviços e comunicação assíncrona com Kafka.
