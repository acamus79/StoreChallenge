package com.aec.store.exceptions;

import java.io.Serial;

public class ArgumentRequiredException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ArgumentRequiredException(String message) {
        super(message);
    }

}

