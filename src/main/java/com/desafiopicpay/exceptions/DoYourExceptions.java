package com.desafiopicpay.exceptions;

import org.springframework.http.HttpStatus;

public class DoYourExceptions extends RuntimeException {
    private  String message;
    private  HttpStatus status;

    public DoYourExceptions(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
