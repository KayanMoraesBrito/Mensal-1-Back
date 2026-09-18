package com.loja.ropa.service;

import com.loja.ropa.dto.ProductCreateDTO;
import com.loja.ropa.dto.ProductDTO;
import com.loja.ropa.dto.ProductUpdateDTO;
import com.loja.ropa.exception.ResourceNotFoundException;
import com.loja.ropa.model.Product;
import com.loja.ropa.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ProductDTO create(ProductCreateDTO dto) {

        log.info("Criando produto: {}", dto.name());

        Product product = Product.builder()
                .name(dto.name())
                .category(dto.category())
                .size(dto.size())
                .price(dto.price())
                .stock(dto.stock())
                .build();

        Product saved = repository.save(product);

        log.info("Produto criado com ID {}", saved.getId());

        return toDTO(saved);
    }

    public List<ProductDTO> findAll() {

        log.info("Listando todos os produtos");

        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ProductDTO findById(Long id) {

        log.info("Buscando produto {}", id);

        Product product = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Produto {} não encontrado", id);
                    return new ResourceNotFoundException(
                            "Produto não encontrado com ID: " + id);
                });

        return toDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductUpdateDTO dto) {

        log.info("Atualizando produto {}", id);

        Product product = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Produto {} não encontrado", id);
                    return new ResourceNotFoundException(
                            "Produto não encontrado com ID: " + id);
                });

        product.setName(dto.name());
        product.setCategory(dto.category());
        product.setSize(dto.size());
        product.setPrice(dto.price());
        product.setStock(dto.stock());

        Product updated = repository.save(product);

        log.info("Produto {} atualizado com sucesso", id);

        return toDTO(updated);
    }

    @Transactional
    public void delete(Long id) {

        log.warn("Removendo produto {}", id);

        if (!repository.existsById(id)) {
            log.error("Produto {} não encontrado para exclusão", id);
            throw new ResourceNotFoundException(
                    "Produto não encontrado com ID: " + id);
        }

        repository.deleteById(id);

        log.info("Produto {} removido", id);
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