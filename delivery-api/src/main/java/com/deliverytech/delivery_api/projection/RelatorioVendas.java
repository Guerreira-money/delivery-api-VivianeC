package com.deliverytech.delivery_api.projection;

import java.math.BigDecimal;

/*Interface de projeção */
public interface RelatorioVendas {

    String getNomeRestaurante();

    BigDecimal getTotalVendas();

    Long getQuantidePedidos();
}
