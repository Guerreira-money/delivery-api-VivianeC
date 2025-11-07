package com.deliverytech.delivery_api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
  
     private Long id;
    private String numeroPedido;
    private LocalDateTime dataPedido;
    private String status;
    private BigDecimal valorTotal;
    private String observacoes;
    private Long clienteId;
    private Long restauranteId;
    private String itens; // JSON ou string representando os itens do pedido

}
