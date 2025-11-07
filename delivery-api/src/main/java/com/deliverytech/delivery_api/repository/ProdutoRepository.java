package com.deliverytech.delivery_api.repository;


import com.deliverytech.delivery_api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
     // buscar produto por restaurante ID
     List<Produto> findByRestauranteId(Long restauranteId);
}
