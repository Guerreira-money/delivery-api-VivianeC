package com.deliverytech.delivery_api.repository;


import com.deliverytech.delivery_api.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    List<Restaurante> findByAtivoTrue();
    List<Restaurante> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);
}
