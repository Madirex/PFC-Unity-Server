package com.madirex.gameserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/rest/services/**")
                        .allowedOrigins("http://localhost:" + APIConfig.API_PORT) //TODO: host sacar de properties
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .maxAge(3600);
            }
        };
    }
}
