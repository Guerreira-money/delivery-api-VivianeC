package com.deliverytech.delivery_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.service.ProdutoService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")

public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@Validated @RequestBody Produto produto) {
        try {
            Produto produtoSalvo = produtoService.cadastrar(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // Listar todos os produtos
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            return ResponseEntity.ok(produtoService.listarTodos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // Buscar produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Produto produto = produtoService.buscarPorId(id);
            if (produto != null) {
                return ResponseEntity.ok(produto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // Atualizar produto
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Validated @RequestBody Produto produto) {
        try {
            Produto atualizado = produtoService.atualizar(id, produto);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // Excluir produto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            produtoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // inativar produto
    @PutMapping("/{id}/inativar")
    public ResponseEntity<?> inativar(@PathVariable Long id) {
        try {
            Produto produtoInativado = produtoService.inativar(id);
            return ResponseEntity.ok(produtoInativado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // buscar produto por restaurante ID
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<?> buscarPorRestaurante(@PathVariable Long restauranteId) {
        try {
            return ResponseEntity.ok(produtoService.buscarPorRestaurante(restauranteId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // Apenas produtos disponíveis
    @GetMapping("/disponiveis")
    public ResponseEntity<?> listarDisponiveis() {
        try {
            return ResponseEntity.ok(produtoService.buscarDisponiveis());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // Produtos por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable String categoria) {
        try {
            return ResponseEntity.ok(produtoService.buscarPorCategoria(categoria));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // Produtos por faixa de preço (menor ou igual)
    @GetMapping("/preco/{preco}")
    public ResponseEntity<?> buscarPorPreco(@PathVariable BigDecimal preco) {
        try {
            return ResponseEntity.ok(produtoService.buscarPorFaixaDePreco(preco));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

}



//Falta mudar usando ApiREsponse, Paged e ErrorREsponse

/*package com.deliverytech.delivery_api.controllers;

import com.deliverytech.delivery_api.dto.response.ApiResponse;
import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // ----------------------------------------
    // POST /produtos  -> cadastrar produto
    // ----------------------------------------
    @PostMapping
    public ResponseEntity<ApiResponse<Produto>> cadastrar(@Validated @RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.cadastrar(produto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(produtoSalvo, "Produto cadastrado com sucesso"));
    }

    // ----------------------------------------
    // GET /produtos  -> listar todos
    // ----------------------------------------
    @GetMapping
    public ResponseEntity<ApiResponse<List<Produto>>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(
                ApiResponse.ok(produtos, "Lista de produtos retornada com sucesso")
        );
    }

    // ----------------------------------------
    // GET /produtos/{id}  -> buscar por ID
    // ----------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Produto>> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);

        if (produto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Produto não encontrado"));
        }

        return ResponseEntity.ok(
                ApiResponse.ok(produto, "Produto encontrado com sucesso")
        );
    }

    // ----------------------------------------
    // PUT /produtos/{id}  -> atualizar produto
    // ----------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Produto>> atualizar(@PathVariable Long id,
                                                          @Validated @RequestBody Produto produto) {
        Produto atualizado = produtoService.atualizar(id, produto);

        return ResponseEntity.ok(
                ApiResponse.ok(atualizado, "Produto atualizado com sucesso")
        );
    }

    // ----------------------------------------
    // DELETE /produtos/{id}  -> excluir produto
    // ----------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> excluir(@PathVariable Long id) {
        produtoService.excluir(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.ok(null, "Produto excluído com sucesso"));
    }

    // ----------------------------------------
    // PUT /produtos/{id}/inativar  -> inativar produto
    // ----------------------------------------
    @PutMapping("/{id}/inativar")
    public ResponseEntity<ApiResponse<Produto>> inativar(@PathVariable Long id) {
        Produto produtoInativado = produtoService.inativar(id);

        return ResponseEntity.ok(
                ApiResponse.ok(produtoInativado, "Produto inativado com sucesso")
        );
    }

    // ----------------------------------------
    // GET /produtos/restaurante/{restauranteId}
    // -> produtos por restaurante
    // ----------------------------------------
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<ApiResponse<List<Produto>>> buscarPorRestaurante(@PathVariable Long restauranteId) {
        List<Produto> produtos = produtoService.buscarPorRestaurante(restauranteId);

        return ResponseEntity.ok(
                ApiResponse.ok(produtos, "Produtos do restaurante retornados com sucesso")
        );
    }

    // ----------------------------------------
    // GET /produtos/disponiveis
    // -> apenas produtos disponíveis
    // ----------------------------------------
    @GetMapping("/disponiveis")
    public ResponseEntity<ApiResponse<List<Produto>>> listarDisponiveis() {
        List<Produto> produtos = produtoService.buscarDisponiveis();

        return ResponseEntity.ok(
                ApiResponse.ok(produtos, "Produtos disponíveis retornados com sucesso")
        );
    }

    // ----------------------------------------
    // GET /produtos/categoria/{categoria}
    // -> produtos por categoria
    // ----------------------------------------
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<ApiResponse<List<Produto>>> buscarPorCategoria(@PathVariable String categoria) {
        List<Produto> produtos = produtoService.buscarPorCategoria(categoria);

        return ResponseEntity.ok(
                ApiResponse.ok(produtos, "Produtos da categoria retornados com sucesso")
        );
    }

    // ----------------------------------------
    // GET /produtos/preco/{preco}
    // -> produtos com preço <= preco
    // ----------------------------------------
    @GetMapping("/preco/{preco}")
    public ResponseEntity<ApiResponse<List<Produto>>> buscarPorPreco(@PathVariable BigDecimal preco) {
        List<Produto> produtos = produtoService.buscarPorFaixaDePreco(preco);

        return ResponseEntity.ok(
                ApiResponse.ok(produtos, "Produtos na faixa de preço retornados com sucesso")
        );
    }

    // ----------------------------------------
    // GET /produtos/nome/{nome}
    // -> produtos por nome (contendo, ignore case)
    // ----------------------------------------
    @GetMapping("/nome/{nome}")
    public ResponseEntity<ApiResponse<List<Produto>>> buscarPorNome(@PathVariable String nome) {
        List<Produto> produtos = produtoService.buscarPorNome(nome);

        return ResponseEntity.ok(
                ApiResponse.ok(produtos, "Produtos contendo o nome informado retornados com sucesso")
        );
    }

}
 */

