package com.aec.store.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    private final List<FieldError> fieldErrors;

    public ValidationException(List<FieldError> fieldErrors) {
        super("There are validation errors.");
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getErrors() {
        return this.fieldErrors;
    }

}
