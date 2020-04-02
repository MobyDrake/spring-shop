package ru.mobydrake.springshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedMediaTypeException extends Exception {

    private static final long serialVersionUID = 1L;

    public UnsupportedMediaTypeException(String message) {
        super(message);
    }


}