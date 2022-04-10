package com.madirex.gameserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class APIConfig {
    //TODO: cogerlo todo mejor del application properties
    @Value("${api.name}") //TODO: esto está bien?
    public static final String API_NAME = "GameServer API";
    @Value("${api.description}") //TODO: esto está bien?
    public static final String API_DESCRIPTION = "API REST para un videojuego en Unity";
    @Value("${api.path}.path")
    public static final String API_PATH = "/rest";
    @Value("${api.version}")
    public static final String API_VERSION = "1.0";
    @Value("${api.port}") //TODO: esto está bien?
    public static final String API_PORT = "6668"; //TODO: esto cogerlo del application properties mejor
    @Value("${api.license}") //TODO: esto está bien?
    public static final String API_LICENSE = "MIT";
    @Value("${api.license}.url") //TODO: esto está bien?
    public static final String API_LICENSE_URL = null;
    @Value("${api.author}") //TODO: esto está bien?
    public static final String AUTHOR_NAME = "Madirex";
    @Value("${api.author}.url") //TODO: esto está bien?
    public static final String AUTHOR_URL = "https://www.madirex.com/";
    @Value("${api.author}.email") //TODO: esto está bien?
    public static final String AUTHOR_EMAIL = "contact@madirex.com";
}
