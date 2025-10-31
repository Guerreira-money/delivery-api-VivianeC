package com.deliverytech.delivery_api.service;


import com.deliverytech.delivery_api.entity.*;
import com.deliverytech.delivery_api.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepo;
    private final RestauranteRepository restauranteRepo;

    public ProdutoService(ProdutoRepository produtoRepo, RestauranteRepository restauranteRepo) {
        this.produtoRepo = produtoRepo;
        this.restauranteRepo = restauranteRepo;
    }

    @Transactional
    public Produto cadastrar(Produto p, Long restauranteId) {
        Restaurante r = restauranteRepo.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));
        p.setRestaurante(r);
        if (p.getPreco() == null || p.getPreco().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Preço inválido");
        p.setAtivo(true);
        return produtoRepo.save(p);
    }

    public List<Produto> listar() {
        return produtoRepo.findByAtivoTrue();
    }

    public List<Produto> listarPorRestaurante(Long restauranteId) {
        Restaurante r = restauranteRepo.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));
        return produtoRepo.findByRestauranteAndAtivoTrue(r);
    }

    @Transactional
    public Produto atualizar(Long id, Produto data) {
        Produto p = produtoRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        if (data.getNome() != null) p.setNome(data.getNome());
        if (data.getDescricao() != null) p.setDescricao(data.getDescricao());
        if (data.getPreco() != null) p.setPreco(data.getPreco());
        return produtoRepo.save(p);
    }

    @Transactional
    public void inativar(Long id) {
        Produto p = produtoRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        p.setAtivo(false);
        produtoRepo.save(p);
    }
}
