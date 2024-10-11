package com.example.texnoeracrm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        //config.setAllowedOrigins(List.of("https://testingfield.fmd.az", "http://localhost:8080","https://supremecourt-project.onrender.com")); // Spesifik origin
        config.addAllowedOriginPattern("*"); // Allow all origins
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // İzin verilen HTTP methodları
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept")); // İzin verilen HTTP başlıkları
        config.setAllowCredentials(true); // Kullanıcı kimlik bilgilerini (örneğin, cookies) destekle
        //source.registerCorsConfiguration("/**", config);
        source.registerCorsConfiguration("/", config);
        return new CorsFilter(source);
    }
}