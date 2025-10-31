package com.deliverytech.delivery_api.repository;
import com.deliverytech.delivery_api.entity.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    
}


