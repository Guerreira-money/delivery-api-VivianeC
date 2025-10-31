package com.deliverytech.delivery_api.dto;
import lombok.Data;
import java.util.List;

@Data
public class CriarPedidoDTO {
    private Long clienteId;
    private Long restauranteId;
    private List<ItemDTO> itens;

    @Data
    public static class ItemDTO {
        private Long produtoId;
        private Integer quantidade;
    }
    
}
