package com.deliverytech.delivery_api.controllers;

import com.deliverytech.delivery_api.dto.CriarPedidoDTO;
import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody CriarPedidoDTO dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Pedido> buscar(@PathVariable String uuid) {
        return ResponseEntity.ok(service.buscarPorUuid(uuid));
    }

    @PutMapping("/{uuid}/status")
    public ResponseEntity<Pedido> atualizarStatus(@PathVariable String uuid,
                                                  @RequestParam Pedido.Status status) {
        return ResponseEntity.ok(service.atualizarStatus(uuid, status));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> cancelar(@PathVariable String uuid) {
        service.cancelar(uuid);
        return ResponseEntity.noContent().build();
    }
}
