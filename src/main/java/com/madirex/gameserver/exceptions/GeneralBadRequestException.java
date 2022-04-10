package com.madirex.gameserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GeneralBadRequestException extends RuntimeException {
    public GeneralBadRequestException(String op, String error) {
        super("Error al procesar: " + op + " | " + error);
    }
}