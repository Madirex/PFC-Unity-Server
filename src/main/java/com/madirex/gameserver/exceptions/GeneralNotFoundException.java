package com.madirex.gameserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GeneralNotFoundException extends RuntimeException {
    public GeneralNotFoundException(String op, String error) {
        super("No se ha encontrado: " + op + " | " + error);
    }
}
