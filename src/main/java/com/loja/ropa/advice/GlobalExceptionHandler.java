package com.loja.ropa.advice;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import com.loja.ropa.exception.ResourceNotFoundException;
import com.loja.ropa.dto.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.validation.BindException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, ex.getMessage(), null));
    }

    @ExceptionHandler({BindException.class, ConstraintViolationException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponse<String>> handleBadRequest(Exception ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(400, "Invalid request: " + ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleAll(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "Server error: " + ex.getMessage(), null));
    }
}
