package com.deliverytech.delivery_api.repository;


import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByAtivoTrue();
    List<Produto> findByRestauranteAndAtivoTrue(Restaurante restaurante);
    List<Produto> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);
}
