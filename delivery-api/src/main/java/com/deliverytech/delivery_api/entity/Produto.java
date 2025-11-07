
package com.deliverytech.delivery_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
@Data @NoArgsConstructor @AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

        
    private String nome;

    
    private BigDecimal preco;

    private String descricao;

    private String categoria;
    private Long restauranteId;

    
    private Boolean disponivel;

    
    private LocalDateTime dataCadastro;

}
