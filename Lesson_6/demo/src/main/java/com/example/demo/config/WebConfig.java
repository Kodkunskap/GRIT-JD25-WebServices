package com.example.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// MVC-baserad CORS-konfiguration. Laddas bara om app.cors.mvc.enabled=true.
// När Spring Security används hanteras CORS i stället i SecurityConfig, eftersom
// Securitys filter körs före Spring MVC och annars stoppar preflight-requests (OPTIONS).
@Configuration
@ConditionalOnProperty(name = "app.cors.mvc.enabled", havingValue = "true")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://127.0.0.1:5501", "http://localhost:*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                .allowCredentials(false);
    }
}
