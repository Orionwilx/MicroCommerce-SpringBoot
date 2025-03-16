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

## Technologies Used
- **Spring Boot**: Main development framework
- **Spring WebFlux**: Reactive programming for high-performance APIs
- **Spring Data JPA/MongoDB**: Data access
- **Docker & Docker Compose**: Containerization and orchestration
- **PostgreSQL & MongoDB**: SQL and NoSQL databases
- **Maven**: Dependency management and build

## Key Features
- Microservices-based architecture
- Reactive programming with WebFlux
- RESTful APIs
- Docker containerization
- Polyglot persistence (SQL and NoSQL)

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

## Challenges Overcome
- Integration of reactive programming with relational databases
- Docker container network configuration
- Implementation of resilience patterns

## Recommendations for Project Enhancement
- Implement API Gateway (Spring Cloud Gateway)
- Add service discovery (Eureka)
- Implement Circuit Breaker with Resilience4j
- Add authentication/authorization with OAuth2/JWT
- Implement unit and integration tests
- Incorporate API documentation with Swagger/OpenAPI
- Configure monitoring with Spring Actuator and Prometheus

 
## Espanish version
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

## Tecnologías utilizadas
- **Spring Boot**: Framework principal de desarrollo
- **Spring WebFlux**: Programación reactiva para APIs de alto rendimiento
- **Spring Data JPA/MongoDB**: Acceso a datos
- **Docker y Docker Compose**: Contenerización y orquestación
- **PostgreSQL y MongoDB**: Bases de datos SQL y NoSQL
- **Maven**: Gestión de dependencias y build

## Características principales
- Arquitectura basada en microservicios
- Programación reactiva con WebFlux
- APIs RESTful
- Contenerización con Docker
- Persistencia polglota (SQL y NoSQL)

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

## Desafíos superados
- Integración de programación reactiva con bases de datos relacionales
- Configuración de redes entre contenedores Docker
- Implementación de patrones de resilencia

## Recomendaciones para mejorar el proyecto
- Implementar un API Gateway (Spring Cloud Gateway)
- Agregar servicio de descubrimiento (Eureka)
- Implementar Circuit Breaker con Resilience4j
- Añadir autenticación/autorización con OAuth2/JWT
- ~Implementar tests unitarios e integración~
- Incorporar documentación de API con Swagger/OpenAPI
- Configurar monitoreo con Spring Actuator y Prometheus

