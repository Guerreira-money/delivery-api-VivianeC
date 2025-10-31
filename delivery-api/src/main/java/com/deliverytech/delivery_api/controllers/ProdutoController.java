package com.deliverytech.delivery_api.controllers;


import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.service.ProdutoService;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping("/{restauranteId}")
    public ResponseEntity<?> cadastrar(@PathVariable Long restauranteId,
                                       @Validated @RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastrar(produto, restauranteId));
    }

    @GetMapping
    public List<Produto> listar() { return service.listar(); }

    @GetMapping("/restaurante/{restauranteId}")
    public List<Produto> listarPorRestaurante(@PathVariable Long restauranteId) {
        return service.listarPorRestaurante(restauranteId);
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto data) {
        return service.atualizar(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        service.inativar(id);
        return ResponseEntity.ok().build();
    }
}
