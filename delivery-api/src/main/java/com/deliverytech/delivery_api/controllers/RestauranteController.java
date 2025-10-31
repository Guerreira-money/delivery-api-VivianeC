package com.deliverytech.delivery_api.controllers;

import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.service.RestauranteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@CrossOrigin(origins = "*")
public class RestauranteController {

    private final RestauranteService service;

    public RestauranteController(RestauranteService service) {
        this.service = service;
    }

    @PostMapping
    public Restaurante cadastrar(@Validated @RequestBody Restaurante r) {
        return service.cadastrar(r);
    }

    @GetMapping
    public List<Restaurante> listar() { return service.listar(); }

    @GetMapping("/buscar")
    public List<Restaurante> buscarPorNome(@RequestParam String nome) {
        return service.buscarPorNome(nome);
    }

    @PutMapping("/{id}")
    public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante data) {
        return service.atualizar(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        service.inativar(id);
        return ResponseEntity.ok().build();
    }
}
