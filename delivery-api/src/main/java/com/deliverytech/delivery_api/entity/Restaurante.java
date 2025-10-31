package com.deliverytech.delivery_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurantes")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Restaurante {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String uuid;

    @Column(nullable = false)
    private String nome;

    private String cnpj;
    private String telefone;
    private String endereco;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Produto> produtos;

    @PrePersist
    public void prePersist() {
        this.uuid = UUID.randomUUID().toString();
        this.ativo = (this.ativo == null) ? true : this.ativo;
        this.dataCadastro = LocalDateTime.now();
    }

    public void inativar() { this.ativo = false; }
}
