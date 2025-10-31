package com.deliverytech.delivery_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    public enum Status {
        CRIADO, CONFIRMADO, ENTREGUE, CANCELADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(nullable = false, unique = true, updatable = false)
    private String uuid;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @ToString.Exclude
    private Cliente cliente;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id")
    @ToString.Exclude
    private Restaurante restaurante;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PedidoItem> itens = new ArrayList<>();

    @Column(scale = 2, precision = 12, nullable = false)
    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    /* Regras */

    public void adicionarItem(PedidoItem item) {
        item.setPedido(this);
        itens.add(item);
        recalcularTotal();

    }

    public void removerItem(PedidoItem item) {
        itens.remove(item);
        item.setPedido(null);
        recalcularTotal();
    }

    public void limparItens() {
        itens.forEach(i -> i.setPedido(null));
        itens.clear();
        recalcularTotal();

    }

    public void recalcularTotal() {
        this.total = itens.stream()
                .map(PedidoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) this.uuid = UUID.randomUUID().toString();
        if (this.status == null) this.status = Status.CRIADO;
        if (this.dataCriacao == null) this.dataCriacao = LocalDateTime.now();
        if (this.total == null) this.total = BigDecimal.ZERO;
        // garante subtotais antes de salvar
        itens.forEach(PedidoItem::atualizaSubtotal);
        recalcularTotal();
    }

    @PreUpdate
    public void preUpdate() {
        itens.forEach(PedidoItem::atualizaSubtotal);
        recalcularTotal();
    }
}
