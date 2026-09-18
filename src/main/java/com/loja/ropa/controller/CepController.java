package com.loja.ropa.controller;

import com.loja.ropa.client.ViaCepClient;
import com.loja.ropa.dto.CepResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cep")
public class CepController {

    private final ViaCepClient client;

    public CepController(ViaCepClient client) {
        this.client = client;
    }

    @Operation(summary = "Consultar CEP")
    @GetMapping("/{cep}")
    public CepResponseDTO buscar(@PathVariable String cep) {
        return client.buscarCep(cep);
    }
}