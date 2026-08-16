package com.loja.ropa.service;

import com.loja.ropa.dto.ProductCreateDTO;
import com.loja.ropa.dto.ProductDTO;
import com.loja.ropa.exception.ResourceNotFoundException;
import com.loja.ropa.model.Product;
import com.loja.ropa.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductDTO create(ProductCreateDTO dto) {
        Product product = Product.builder()
                .name(dto.name())
                .category(dto.category())
                .size(dto.size())
                .price(dto.price())
                .stock(dto.stock())
                .build();
        Product saved = repository.save(product);
        return toDTO(saved);
    }

    public List<ProductDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public ProductDTO findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        return toDTO(product);
    }

    public ProductDTO update(Long id, ProductCreateDTO dto) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        product.setName(dto.name());
        product.setCategory(dto.category());
        product.setSize(dto.size());
        product.setPrice(dto.price());
        product.setStock(dto.stock());

        Product updated = repository.save(product);
        return toDTO(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getSize(),
                product.getPrice(),
                product.getStock()
        );
    }
}