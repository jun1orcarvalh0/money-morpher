package com.ada.moneymorpher.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends AbstractException{
    public ForbiddenException(String message){
        super(message);
    }
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
