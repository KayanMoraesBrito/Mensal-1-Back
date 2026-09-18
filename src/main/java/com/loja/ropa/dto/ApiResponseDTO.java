package com.loja.ropa.dto;

public record ApiResponseDTO<T>(int status, String message, T data) {}
