package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.dto.CriarPedidoDTO;
import com.deliverytech.delivery_api.entity.*;
import com.deliverytech.delivery_api.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorUuid(String uuid) {
        return pedidoRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }

    @Transactional
    public Pedido criar(CriarPedidoDTO dto) {
        var cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente inválido"));
        var restaurante = restauranteRepository.findById(dto.getRestauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurante inválido"));

        var pedido = Pedido.builder()
                .cliente(cliente)
                .restaurante(restaurante)
                .build();

        dto.getItens().forEach(i -> {
            var produto = produtoRepository.findById(i.getProdutoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + i.getProdutoId()));

            var item = PedidoItem.builder()
                    .produto(produto)
                    .quantidade(i.getQuantidade() == null ? 1 : i.getQuantidade())
                    .precoUnitario(produto.getPreco()) // congela o preço do momento
                    .build();

            pedido.adicionarItem(item);
        });

        // PrePersist calcula total/subtotais
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido atualizarStatus(String uuid, Pedido.Status novoStatus) {
        var pedido = buscarPorUuid(uuid);
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(String uuid) {
        var pedido = buscarPorUuid(uuid);
        pedido.setStatus(Pedido.Status.CANCELADO);
        pedidoRepository.save(pedido);
    }
}
