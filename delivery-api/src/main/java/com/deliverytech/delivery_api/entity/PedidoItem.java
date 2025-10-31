package com.deliverytech.delivery_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pedido_itens")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PedidoItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    @ToString.Exclude
    private Pedido pedido;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantidade = 1;

    /** preço congelado no momento do pedido */
    @Column(name = "preco_unitario", scale = 2, precision = 12, nullable = false)
    private BigDecimal precoUnitario;

    @Column(scale = 2, precision = 12, nullable = false)
    @Builder.Default
    private BigDecimal subtotal = BigDecimal.ZERO;

    public void atualizaSubtotal() {
        if (precoUnitario == null) precoUnitario = BigDecimal.ZERO;
        if (quantidade == null) quantidade = 0;
        this.subtotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    @PrePersist @PreUpdate
    public void prePersist() { atualizaSubtotal(); }
}
