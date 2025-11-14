package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.*;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
     List<Produto> findByRestauranteId(Long restauranteId);

     // Apenas produtos disponíveis
     List<Produto> findByDisponivelTrue();

// Buscar produtos cujo nome contenha o texto (case insensitive)
List<Produto> findByNomeContainingIgnoreCase(String nome);


     // Produtos por categoria
     List<Produto> findByCategoria(String categoria);

     // Por faixa de preço (menor ou igual)
     List<Produto> findByPrecoLessThanEqual(BigDecimal preco);
}
