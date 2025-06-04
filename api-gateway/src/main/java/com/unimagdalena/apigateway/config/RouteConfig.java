package com.unimagdalena.apigateway.config;

import com.unimagdalena.apigateway.filter.factory.ProductCachingFilterFactory;
import com.unimagdalena.apigateway.filter.factory.ServiceAuthHeaderFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.Duration;


@Configuration
public class RouteConfig {



    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           ServiceAuthHeaderFilterFactory authFilterFactory, ProductCachingFilterFactory productCachingFilterFactory) {
        return builder.routes()

                //RUTA ORDER-SERVICE
                .route("order-service", r -> r
                        .path("/api/orders/**")
                        .filters(f -> f
                                .filter(authFilterFactory
                                        .apply(c -> {
                                            c.setHeaderName("X-Service-Auth");
                                            c.setHeaderValue("order-service-key");
                                    })
                                )
                                .retry(retry -> retry
                                        .setRetries(3) // Número de reintentos
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.GATEWAY_TIMEOUT) // Reintenta en estos errores
                                        .setMethods(HttpMethod.GET) // Aplica solo a GET (importante para evitar reintentos en operaciones POST/PUT)
                                        .setBackoff(Duration.ofMillis(200), Duration.ofSeconds(3), 2, true)
                                )
                                .circuitBreaker(config -> config
                                        .setName("orderServiceCB")
                                        .setFallbackUri("forward:/fallback/orders")
                                ))
                        .uri("lb://order-service"))

                //RUTA PRODUCT-SERVICE
                .route("product-service", r -> r
                        .path("/api/products/**")
                        .filters(f -> f
                                .filter(productCachingFilterFactory
                                        .apply(c -> {
                                            c.setTtlSeconds(300);
                                        })
                                )
                                .retry(retry -> retry
                                        .setRetries(3) // Número de reintentos
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.GATEWAY_TIMEOUT) // Reintenta en estos errores
                                        .setMethods(HttpMethod.GET) // Aplica solo a GET (importante para evitar reintentos en operaciones POST/PUT)
                                        .setBackoff(Duration.ofMillis(200), Duration.ofSeconds(3), 2, true)
                                )
                                .circuitBreaker(config -> config
                                        .setName("productServiceCB")
                                        .setFallbackUri("forward:/fallback/products")
                                )
                        )
                        .uri("lb://product-service"))

                //RUTA INVENTORY-SERVICE
                .route("inventory-service", r -> r
                        .path("/api/inventory/**")
                        .filters(f -> f
                                .retry(retry -> retry
                                        .setRetries(3) // Número de reintentos
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.GATEWAY_TIMEOUT) // Reintenta en estos errores
                                        .setMethods(HttpMethod.GET) // Aplica solo a GET (importante para evitar reintentos en operaciones POST/PUT)
                                        .setBackoff(Duration.ofMillis(200), Duration.ofSeconds(3), 2, true)
                                )
                                .circuitBreaker(config -> config
                                        .setName("inventoryServiceCB")
                                        .setFallbackUri("forward:/fallback/inventory")
                                )
                        )
                        .uri("lb://inventory-service"))

                //RUTA PAYMENT SERVICE
                .route("payment-service", r -> r
                        .path("/api/payments/**")
                        .filters(f -> f
                                .retry(retry -> retry
                                        .setRetries(3) // Número de reintentos
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.GATEWAY_TIMEOUT) // Reintenta en estos errores
                                        .setMethods(HttpMethod.GET) // Aplica solo a GET (importante para evitar reintentos en operaciones POST/PUT)
                                        .setBackoff(Duration.ofMillis(200), Duration.ofSeconds(3), 2, true)
                                )
                                .circuitBreaker(config -> config
                                        .setName("paymentServiceCB")
                                        .setFallbackUri("forward:/fallback/payments")
                                )
                        )
                        .uri("lb://payment-service"))
                //RUTA KEYCLOAK
                .route("keycloak-token", r -> r
                        .path("/auth")
                        .filters(f -> f.setPath("/realms/master/protocol/openid-connect/token"))
                        .uri("http://keycloak:8080"))

                //APARTIR DE AQUI ESTAN LAS RUTAS PARA EL FUNCIONAMIENTO DEL CIRCUIT-BREAKER

                //FALLBACK OREDER SERVICE
                .route("order-fallback-route", r -> r
                        .path("/fallback/orders")
                        .filters(f -> f
                                .setStatus(HttpStatus.SERVICE_UNAVAILABLE)
                        )
                        .uri("forward:/fallback-response/orders-unavailable")
                )

                //FALLBACK PRODUCT SERVICE
                .route("product-fallback-route", r -> r
                        .path("/fallback/products")
                        .filters(f -> f
                                .setStatus(HttpStatus.SERVICE_UNAVAILABLE)
                        )
                        .uri("forward:/fallback-response/products-unavailable")
                )

                //FALLBACK INVETORY SERVICE
                .route("inventory-fallback-route", r -> r
                        .path("/fallback/inventory")
                        .filters(f -> f
                                .setStatus(HttpStatus.SERVICE_UNAVAILABLE)
                        )
                        .uri("forward:/fallback-response/inventory-unavailable")
                )

                //FALLBACK PAYMENT service
                .route("payment-fallback-route", r -> r
                        .path("/fallback/payments")
                        .filters(f -> f
                                .setStatus(HttpStatus.SERVICE_UNAVAILABLE)
                        )
                        .uri("forward:/fallback-response/payments-unavailable")
                )
                .build();


    }
}