package com.loja.ropa.controller;

import com.loja.ropa.dto.ApiResponse;
import com.loja.ropa.dto.ProductCreateDTO;
import com.loja.ropa.dto.ProductDTO;
import com.loja.ropa.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> create(@RequestBody @Valid ProductCreateDTO dto) {
        ProductDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Produto criado com sucesso", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> findAll() {
        List<ProductDTO> products = service.findAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Lista de produtos recuperada", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> findById(@PathVariable Long id) {
        ProductDTO product = service.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Produto encontrado", product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> update(@PathVariable Long id, @RequestBody @Valid ProductCreateDTO dto) {
        ProductDTO updated = service.update(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(200, "Produto atualizado com sucesso", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Produto removido com sucesso", null));
    }
}