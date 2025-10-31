package com.deliverytech.delivery_api.repository;


import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.entity.Cliente;
import com.deliverytech.delivery_api.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteOrderByDataCriacaoDesc(Cliente cliente);
    List<Pedido> findByRestauranteOrderByDataCriacaoDesc(Restaurante restaurante);
    Optional<Pedido> findByUuid(String uuid);
}
