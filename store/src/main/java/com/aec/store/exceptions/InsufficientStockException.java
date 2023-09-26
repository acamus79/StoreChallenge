package com.aec.store.exceptions;

import java.io.Serial;

public class InsufficientStockException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 15L;

    public InsufficientStockException(String message) {
        super(message);
    }


}
