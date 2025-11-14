package com.deliverytech.delivery_api.controllers;

import com.deliverytech.delivery_api.projection.RelatorioVendas;
import com.deliverytech.delivery_api.service.RestauranteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RestauranteService restauranteService;

    public RelatorioController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    // GET /relatorios/vendas-por-restaurante
    @GetMapping("/vendas-por-restaurante")
    public ResponseEntity<List<RelatorioVendas>> vendasPorRestaurante() {
        List<RelatorioVendas> relatorio = restauranteService.relatorioVendasPorRestaurante();
        return ResponseEntity.ok(relatorio);
    }
}
