package com.deliverytech.delivery_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import java.util.List;


@Entity
@Table(name = "restaurantes")
@Data 
@NoArgsConstructor
@AllArgsConstructor

public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String categoria;

    private String endereco;

    private String telefone;

    @Column(name = "taxa_entrega")
    private BigDecimal taxaEntrega;

    private BigDecimal avaliacao;

    private Boolean ativo;

    public void inativar() {
        this.ativo = false;
    }
}
