# E-Commerce Microservices System

## Description
E-commerce system based on microservices architecture built with Spring Boot. This architecture enables scalability, resilience, and independent development of each component.

## Architecture

![Microservices Architecture](https://www.hiberus.com/crecemos-contigo/wp-content/uploads/2019/03/microservicios-estructura-1024x550.png)

### Implemented Services:
- **Product Service**: Manages product catalog (MongoDB)
- **Order Service**: Handles orders and checkout
- **Inventory Service**: Stock control
- **Payment Service**: Payment processing (PostgreSQL)
- **Eureka Server**: Service discovery for registering and locating microservices
- **API Gateway**: Single entry point for all client requests

## Technologies Used
- **Spring Boot**: Main development framework
- **Spring WebFlux**: Reactive programming for high-performance APIs
- **Spring Data JPA/MongoDB**: Data access
- **Docker & Docker Compose**: Containerization and orchestration
- **PostgreSQL & MongoDB**: SQL and NoSQL databases
- **Eureka**: Service registration and discovery
- **Spring Cloud Gateway**: API Gateway for centralized routing
- **Maven**: Dependency management and build
- **JUnit & Mockito**: Unit and integration testing

## Key Features
- Microservices-based architecture
- Reactive programming with WebFlux
- RESTful APIs
- Docker containerization
- Polyglot persistence (SQL and NoSQL)
- Service discovery with Eureka
- Centralized routing with API Gateway
- Unit and integration tests

## Setup and Deployment
```bash
# Clone the repository
git clone https://github.com/username/ecommerce-microservices.git

# Build services
./mvnw clean package -DskipTests

# Deploy with Docker Compose
docker-compose up -d
```

## Main API Endpoints
All endpoints are available through the API Gateway at: `http://localhost:8090`

```
# Products
GET    /api/products          - List all products
POST   /api/products          - Create product
GET    /api/products/{id}     - View product details
PUT    /api/products/{id}     - Update product
DELETE /api/products/{id}     - Delete product

# Payments
GET    /api/payments          - List all payments
POST   /api/payments          - Process new payment
GET    /api/payments/{id}     - View payment details
PUT    /api/payments/{id}     - Update payment
DELETE /api/payments/{id}     - Delete payment

# Orders
GET    /api/orders            - List all orders
POST   /api/orders            - Create order
GET    /api/orders/{id}       - View order details
PUT    /api/orders/{id}       - Update order
DELETE /api/orders/{id}       - Delete order

# Inventory
GET    /api/inventory         - List all inventory products
POST   /api/inventory         - Create new inventory product
GET    /api/inventory/{id}    - View inventory product details
PUT    /api/inventory/{id}    - Update inventory product
DELETE /api/inventory/{id}    - Delete inventory product
```

## Infrastructure Services
- **Eureka Server**: http://localhost:8761
- **API Gateway**: http://localhost:8090

## Challenges Overcome
- Integration of reactive programming with relational databases
- Docker container network configuration
- Implementation of resilience patterns
- Configuration of multiple service replicas with Docker Compose

## Recommendations for Project Enhancement
- ~~Implement API Gateway (Spring Cloud Gateway)~~
- ~~Add service discovery (Eureka)~~
- Implement Circuit Breaker with Resilience4j
- Add authentication/authorization with OAuth2/JWT
- ~~Implement unit and integration tests~~
- Incorporate API documentation with Swagger/OpenAPI
- Configure monitoring with Spring Actuator and Prometheus

# **_Spanish Version_**

# Microservicio de Comercio Electrónico

## Descripción
Sistema de comercio electrónico basado en microservicios construido con Spring Boot. Esta arquitectura permite escalabilidad, resilencia y desarrollo independiente de cada componente.

## Arquitectura

![Arquitectura de Microservicios](https://www.hiberus.com/crecemos-contigo/wp-content/uploads/2019/03/microservicios-estructura-1024x550.png)

### Servicios implementados:
- **Product Service**: Gestiona el catálogo de productos (MongoDB)
- **Order Service**: Maneja pedidos y checkout
- **Inventory Service**: Control de stock
- **Payment Service**: Procesamiento de pagos (PostgreSQL)
- **Eureka Server**: Servicio de descubrimiento que registra y localiza microservicios
- **API Gateway**: Punto único de entrada para todas las solicitudes de clientes

## Tecnologías utilizadas
- **Spring Boot**: Framework principal de desarrollo
- **Spring WebFlux**: Programación reactiva para APIs de alto rendimiento
- **Spring Data JPA/MongoDB**: Acceso a datos
- **Docker y Docker Compose**: Contenerización y orquestación
- **PostgreSQL y MongoDB**: Bases de datos SQL y NoSQL
- **Eureka**: Registro y descubrimiento de servicios
- **Spring Cloud Gateway**: API Gateway para enrutamiento centralizado
- **Maven**: Gestión de dependencias y build
- **JUnit & Mockito**: Pruebas unitarias y de integración

## Características principales
- Arquitectura basada en microservicios
- Programación reactiva con WebFlux
- APIs RESTful
- Contenerización con Docker
- Persistencia polglota (SQL y NoSQL)
- Descubrimiento de servicios con Eureka
- Enrutamiento centralizado con API Gateway
- Tests unitarios y de integración

## Configuración y despliegue
```bash
# Clonar el repositorio
git clone https://github.com/usuario/ecommerce-microservices.git

# Construir los servicios
./mvnw clean package -DskipTests

# Desplegar con Docker Compose
docker-compose up -d
```

## Endpoints API principales
Todos los endpoints están disponibles a través del API Gateway en: `http://localhost:8090`

```
# Productos
GET    /api/products          - Listar todos los productos
POST   /api/products          - Crear producto
GET    /api/products/{id}     - Ver detalle de producto
PUT    /api/products/{id}     - Actualizar producto
DELETE /api/products/{id}     - Eliminar producto

# Pagos
GET    /api/payments          - Listar todos los pagos
POST   /api/payments          - Procesar nuevo pago
GET    /api/payments/{id}     - Ver detalle de pago
PUT    /api/payments/{id}     - Actualizar pago
DELETE /api/payments/{id}     - Eliminar pago

# Pedidos
GET    /api/orders            - Listar todos los pedidos
POST   /api/orders            - Crear pedido
GET    /api/orders/{id}       - Ver detalle de pedido
PUT    /api/orders/{id}       - Actualizar pedido
DELETE /api/orders/{id}       - Eliminar pedido

# Inventario
GET    /api/inventory         - Listar todos los productos en inventario
POST   /api/inventory         - Crear nuevo producto en inventario
GET    /api/inventory/{id}    - Ver detalle de producto en inventario
PUT    /api/inventory/{id}    - Actualizar producto en inventario
DELETE /api/inventory/{id}    - Eliminar producto en inventario

```


## Servicios de infraestructura
- **Eureka Server**: http://localhost:8761
- **API Gateway**: http://localhost:8090

## Desafíos superados
- Integración de programación reactiva con bases de datos relacionales
- Configuración de redes entre contenedores Docker
- Implementación de patrones de resilencia
- Configuración de múltiples réplicas de servicios mediante Docker Compose

## Recomendaciones para mejorar el proyecto
- ~~Implementar un API Gateway (Spring Cloud Gateway)~~
- ~~Agregar servicio de descubrimiento (Eureka)~~
- Implementar Circuit Breaker con Resilience4j
- Añadir autenticación/autorización con OAuth2/JWT
- ~~Implementar tests unitarios e integración~~
- Incorporar documentación de API con Swagger/OpenAPI
- Configurar monitoreo con Spring Actuator y Prometheus

## Pendientes 
 - Terminar integracion correcta de circuit breaker con resilience4j
 - Agregar observabilidad con spring actuator
