package tech.matiaszelarayan.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    // Mapa thread-safe para asociar cada IP con su balde de peticiones
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    // Crea un balde de 15 peticiones que se recarga cada minuto
    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.builder()
                .capacity(15)
                .refillIntervally(15, Duration.ofMinutes(1))
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Solo aplicamos el límite a los endpoints de la API del chat
        if (request.getRequestURI().startsWith("/api/chat")) {
            String clientIp = getClientIP(request);
            Bucket bucket = buckets.computeIfAbsent(clientIp, k -> createNewBucket());

            // Intentamos consumir 1 ficha
            if (!bucket.tryConsume(1)) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // HTTP 429
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("""
                    {
                      "error": "Has alcanzado el límite de consultas (máximo 15 por minuto). Espera un momento antes de reintentar."
                    }
                    """);
                return; // Detiene la petición
            }
        }

        filterChain.doFilter(request, response);
    }

    // Obtiene la IP real, contemplando proxies o servicios de cloud (Render/OCI)
    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty()) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }
}