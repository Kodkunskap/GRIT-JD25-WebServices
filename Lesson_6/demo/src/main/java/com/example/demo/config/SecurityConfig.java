package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Använder corsConfigurationSource-bönan nedan. CORS måste hanteras här,
            // annars stoppar Security preflight-requests (OPTIONS) innan de når Spring MVC.
            .cors(Customizer.withDefaults())
            // Stateless REST-API utan sessionscookies, därför behövs inget CSRF-skydd.
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/**").authenticated()
                    // Swagger UI och OpenAPI-dokumentet som UI:t hämtar
                    .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .anyRequest().denyAll())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://127.0.0.1:5501", "http://localhost:*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /*
            DelegatingPasswordEncoder - använder rätt hash-algoritm beroende
            på ett prefix i hash:en - t.ex. {bcrypt}x7asdkzxcsad88asd säger
            att den följande hash:en är bcrypt hashad.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Användare i minnet för demo. Byts senare mot användare från databasen.
    //    @Bean
    //    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    //        return new InMemoryUserDetailsManager(
    //            User.withUsername("user")
    //                .password(passwordEncoder.encode("password"))
    //                .roles("USER")
    //                .build(),
    //            User.withUsername("admin")
    //                .password(passwordEncoder.encode("admin"))
    //                .roles("ADMIN", "USER")
    //                .build()
    //        );
    //    }
}
