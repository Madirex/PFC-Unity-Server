package com.madirex.gameserver.config;

import com.madirex.gameserver.config.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class APIConfig {
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

    public static final String TEST_TOKEN = JwtTokenProvider.TOKEN_PREFIX + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyNjFlYWZhOS00NjExLTQ0ZTktOWNmZS02ZWRlNGI0MTMyNGEiLCJpYXQiOjE2NTQ0NDIyMjcsImV4cCI6MTY1NDUyODYyNywibmFtZSI6IkFkbWluIiwicm9sZXMiOiJBRE1JTiwgUExBWUVSIn0.YgN-IDXfvG1utMwuY6XXQZFC5E1Bh5PbslGCWI5ExEr2yngjKFOX2lbj5md_Gx12Tiv8xhpp-5aOOgzoJuYBPQ";
}
