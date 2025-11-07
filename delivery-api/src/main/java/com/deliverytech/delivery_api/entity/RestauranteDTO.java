package com.deliverytech.delivery_api.entity;
import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteDTO {
    
    private Long id;
    private String nome;
    private String categoria;
    private String endereco;
    private String telefone;
    private BigDecimal taxaEntrega;
    private BigDecimal avaliacao;
    private Boolean ativo;
}
