package com.deliverytech.delivery_api.repository;


import com.deliverytech.delivery_api.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    List<Restaurante> findByAtivoTrue();
    List<Restaurante> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);
    
    // Buscar por categoria
    List<Restaurante> findByCategoria(String categoria);
}


