package com.deliverytech.delivery_api.config;

import com.deliverytech.delivery_api.entity.Cliente;
import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import com.deliverytech.delivery_api.repository.PedidoRepository;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    public DataLoader(ClienteRepository clienteRepository,
            RestauranteRepository restauranteRepository,
            ProdutoRepository produtoRepository,
            PedidoRepository pedidoRepository) {
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(String... args) {

        // Evita recriar tudo a cada restart
        if (clienteRepository.count() > 0) {
            System.out.println("DataLoader: dados já existentes, não será recarregado.");
            return;
        }

        LocalDateTime agora = LocalDateTime.now();

        
        // 1. CLIENTES (3 clientes)
        
        Cliente c1 = new Cliente();
        c1.setNome("Ana Souza");
        c1.setEmail("ana@example.com");
        c1.setTelefone("11999990001");
        c1.setEndereco("Rua A, 123");
        c1.setDataCadastro(agora);
        c1.setAtivo(true);

        Cliente c2 = new Cliente();
        c2.setNome("Bruno Silva");
        c2.setEmail("bruno@example.com");
        c2.setTelefone("21988887777");
        c2.setEndereco("Rua B, 456");
        c2.setDataCadastro(agora);
        c2.setAtivo(true);

        Cliente c3 = new Cliente();
        c3.setNome("Carla Mendes");
        c3.setEmail("carla@example.com");
        c3.setTelefone("31977776666");
        c3.setEndereco("Rua C, 789");
        c3.setDataCadastro(agora);
        c3.setAtivo(true);

        clienteRepository.saveAll(List.of(c1, c2, c3));

        // Após salvar, os IDs são preenchidos
        System.out.println("Clientes criados: " + clienteRepository.count());

        // -------------------------------
        // 2. RESTAURANTES (2 restaurantes)
        // -------------------------------
        Restaurante r1 = new Restaurante();
        r1.setNome("Pizzaria Saborosa");
        r1.setCategoria("Pizza");
        r1.setEndereco("Av. Central, 100");
        r1.setTelefone("1133330000");
        r1.setTaxaEntrega(new BigDecimal("5.00"));
        r1.setAvaliacao(new BigDecimal("4.5"));
        r1.setAtivo(true);

        Restaurante r2 = new Restaurante();
        r2.setNome("Hamburgueria da Vila");
        r2.setCategoria("Lanches");
        r2.setEndereco("Rua das Flores, 200");
        r2.setTelefone("1144441111");
        r2.setTaxaEntrega(new BigDecimal("7.50"));
        r2.setAvaliacao(new BigDecimal("4.2"));
        r2.setAtivo(true);

        r1 = restauranteRepository.save(r1);
        r2 = restauranteRepository.save(r2);

        System.out.println("Restaurantes criados: " + restauranteRepository.count());

        // -------------------------------
        // 3. PRODUTOS (5 produtos)
        // -------------------------------
        Produto p1 = new Produto();
        p1.setNome("Pizza Margherita");
        p1.setDescricao("Pizza com mussarela, tomate e manjericão");
        p1.setPreco(new BigDecimal("35.90"));
        p1.setCategoria("Pizza");
        p1.setDisponivel(true);
        p1.setRestauranteId(r1.getId());

        Produto p2 = new Produto();
        p2.setNome("Pizza Calabresa");
        p2.setDescricao("Pizza de calabresa com cebola");
        p2.setPreco(new BigDecimal("38.50"));
        p2.setCategoria("Pizza");
        p2.setDisponivel(true);
        p2.setRestauranteId(r1.getId());

        Produto p3 = new Produto();
        p3.setNome("Cheeseburger");
        p3.setDescricao("Hambúrguer com queijo e salada");
        p3.setPreco(new BigDecimal("22.00"));
        p3.setCategoria("Lanche");
        p3.setDisponivel(true);
        p3.setRestauranteId(r2.getId());

        Produto p4 = new Produto();
        p4.setNome("Batata Frita Média");
        p4.setDescricao("Porção de batata frita média");
        p4.setPreco(new BigDecimal("12.00"));
        p4.setCategoria("Acompanhamento");
        p4.setDisponivel(true);
        p4.setRestauranteId(r2.getId());

        Produto p5 = new Produto();
        p5.setNome("Refrigerante Lata");
        p5.setDescricao("Refrigerante 350ml");
        p5.setPreco(new BigDecimal("6.00"));
        p5.setCategoria("Bebida");
        p5.setDisponivel(true);
        p5.setRestauranteId(r2.getId());

        produtoRepository.saveAll(List.of(p1, p2, p3, p4, p5));

        System.out.println("Produtos criados: " + produtoRepository.count());

        
        // 4. PEDIDOS (2 pedidos simples)

        Pedido ped1 = new Pedido();
        ped1.setNumeroPedido("PED-0001");
        ped1.setDataPedido(agora);
        ped1.setStatus("RECEBIDO"); // entidade usa String status
        ped1.setValorTotal(p1.getPreco()); // 1x Pizza Margherita
        ped1.setObservacoes("Sem borda recheada");
        ped1.setClienteId(c1.getId());
        ped1.setRestaurante(r1);
        ped1.setItens("Pizza Margherita x1");

        Pedido ped2 = new Pedido();
        ped2.setNumeroPedido("PED-0002");
        ped2.setDataPedido(agora);
        ped2.setStatus("PREPARANDO");
        ped2.setValorTotal(
                p3.getPreco()
                        .add(p4.getPreco())
                        .add(p5.getPreco())); // 1x Cheeseburger + Batata + Refri
        ped2.setObservacoes("Entrega rápida, por favor");
        ped2.setClienteId(c2.getId());
        ped2.setRestaurante(r2);
        ped2.setItens("Cheeseburger x1; Batata Frita Média x1; Refrigerante Lata x1");

        pedidoRepository.saveAll(List.of(ped1, ped2));

        System.out.println("Pedidos criados: " + pedidoRepository.count());
        System.out.println("==== DataLoader: Dados de teste carregados com sucesso! ====");
    }
}
