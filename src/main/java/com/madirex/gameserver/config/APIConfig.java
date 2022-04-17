package com.madirex.gameserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class APIConfig {
    //TODO: cogerlo todo mejor del application properties
    @Value("${api.name}")
    public static final String API_NAME = "GameServer API";
    @Value("${api.description}")
    public static final String API_DESCRIPTION = "API REST para un videojuego en Unity";
    @Value("${api.path}.path")
    public static final String API_PATH = "/rest";
    @Value("${api.version}")
    public static final String API_VERSION = "1.0";
    @Value("${api.port}")
    public static final String API_PORT = "6668";
    @Value("${api.license}")
    public static final String API_LICENSE = "MIT";
    @Value("${api.license}.url")
    public static final String API_LICENSE_URL = null;
    @Value("${api.author}")
    public static final String AUTHOR_NAME = "Madirex";
    @Value("${api.author}.url")
    public static final String AUTHOR_URL = "https://www.madirex.com/";
    @Value("${api.author}.email")
    public static final String AUTHOR_EMAIL = "contact@madirex.com";

    //TODO: hacer algo con el TEST TOKEN para no tener que editarlo cada vez que se quiera testear
    public static final String TEST_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
            "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
            "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
            "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg";
}
