
package com.deliverytech.delivery_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "produtos")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Produto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String uuid;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, scale = 2, precision = 12)
    private BigDecimal preco;

    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Restaurante restaurante;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @PrePersist
    public void prePersist() {
        this.uuid = UUID.randomUUID().toString();
        this.ativo = (this.ativo == null) ? true : this.ativo;
        this.dataCadastro = LocalDateTime.now();
    }
}
