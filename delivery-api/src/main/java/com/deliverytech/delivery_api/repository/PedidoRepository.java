package com.deliverytech.delivery_api.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.entity.Pedido;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteOrderByDataCriacaoDesc(Long clienteId);

    List<Pedido> findByRestauranteOrderByDataCriacaoDesc(Long restauranteId);

     // Buscar por número do pedido
    Pedido findByNumeroPedido(String numeroPedido);
    
    Optional<Pedido> findByUuid(String uuid);
}

