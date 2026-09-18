package com.loja.ropa.controller;

import com.loja.ropa.dto.ProductCreateDTO;
import com.loja.ropa.dto.ProductDTO;
import com.loja.ropa.dto.ProductUpdateDTO;
import com.loja.ropa.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Produtos", description = "Operações de produtos")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Listar produtos")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Buscar produto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Cadastrar produto")
    @PostMapping
    public ResponseEntity<ProductDTO> criar(
            @Valid @RequestBody ProductCreateDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    @Operation(summary = "Atualizar produto")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateDTO dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Excluir produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}