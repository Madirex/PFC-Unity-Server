package com.madirex.gameserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameServerApplication.class, args);
    }
}


/*
TODO: - TESTINGS DEL SERVIDOR:
	1. Hacer que todos los MOCK de controladores funcionen
	2. Hacer que todos los Mock MVC Test de controladores funcionen
	3. Hacer que todos los tests de Integration funcionen
	4. Cobertura 100%
    - COMENTARIOS TODO Y FIXME DEL SERVIDOR
    - PASAR A KOTLIN
    - DOCUMENTAR ABSOLUTAMENTE TODO
 */