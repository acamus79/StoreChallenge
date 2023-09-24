package com.aec.store.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationUtils {

    /**
     * Handle validation errors and return a response if there are errors.
     *
     * @param errors Validation errors
     * @return ResponseEntity with validation error details or null if no errors
     */
    public static ResponseEntity<Map<String, Object>> handleValidationErrors(Errors errors) {
        Map<String, Object> response = new HashMap<>();
        if (errors.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (FieldError error : errors.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            response.put("status", "error");
            response.put("message", "Validation failed");
            response.put("errors", errorMessages);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return null;
    }
}

