package com.loja.ropa.advice;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import com.loja.ropa.exception.ResourceNotFoundException;
import com.loja.ropa.dto.ApiResponseDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.validation.BindException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDTO<>(404, ex.getMessage(), null));
    }

    @ExceptionHandler({BindException.class, ConstraintViolationException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponseDTO<String>> handleBadRequest(Exception ex) {
        return ResponseEntity.badRequest().body(new ApiResponseDTO<>(400, "Invalid request: " + ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<String>> handleAll(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDTO<>(500, "Server error: " + ex.getMessage(), null));
    }
}
