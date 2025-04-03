package com.unimagdalena.apigateway.filter.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class correlationIdFIlter implements GlobalFilter, Ordered {
    
    private static final Logger logger = LoggerFactory.getLogger(correlationIdFIlter.class);
    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Generar un UUID para el Correlation ID
        String correlationId = UUID.randomUUID().toString();

        // Agregarlo a los headers de la petición
        ServerHttpRequest mutatedRequest = request.mutate()
                .header(CORRELATION_ID_HEADER, correlationId)
                .build();

        logger.info("📌 Nueva solicitud con CorrelationId: {}", correlationId);

        return chain.filter(exchange.mutate().request(mutatedRequest).build())
                .then(Mono.fromRunnable(() -> {
                    // Agregar el mismo CorrelationId a la respuesta
                    ServerHttpResponse response = exchange.getResponse();
                    response.getHeaders().add(CORRELATION_ID_HEADER, correlationId);
                    logger.info("✅ Respuesta con CorrelationId: {}", correlationId);
                }));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
