package com.deliverytech.delivery_api.service;


import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteService {

    private final RestauranteRepository repo;

    public RestauranteService(RestauranteRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Restaurante cadastrar(Restaurante r) {
        r.setAtivo(true);
        return repo.save(r);
    }

    public List<Restaurante> listar() {
        return repo.findByAtivoTrue();
    }

    @Transactional
    public Restaurante atualizar(Long id, Restaurante data) {
        Restaurante r = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));
        if (data.getNome() != null) r.setNome(data.getNome());
        if (data.getEndereco() != null) r.setEndereco(data.getEndereco());
        if (data.getTelefone() != null) r.setTelefone(data.getTelefone());
        if (data.getCnpj() != null) r.setCnpj(data.getCnpj());
        return repo.save(r);
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante r = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));
        r.inativar();
        repo.save(r);
    }

    public List<Restaurante> buscarPorNome(String nome) {
        return repo.findByNomeContainingIgnoreCaseAndAtivoTrue(nome);
    }
}
