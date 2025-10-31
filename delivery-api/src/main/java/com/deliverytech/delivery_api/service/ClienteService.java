package com.deliverytech.delivery_api.service;


import com.deliverytech.delivery_api.entity.Cliente;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Cliente cadastrar(Cliente c) {
        if (c.getEmail() == null || c.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        c.setAtivo(true);
        if (c.getDataCadastro() == null) c.setDataCadastro(LocalDateTime.now());
        return repo.save(c);
    }

    public List<Cliente> listarAtivos() {
        return repo.findByAtivoTrue();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return repo.findById(id).filter(Cliente::getAtivo);
    }

    @Transactional
    public Cliente atualizar(Long id, Cliente data) {
        Cliente c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        if (Boolean.FALSE.equals(c.getAtivo())) throw new IllegalArgumentException("Cliente inativo");
        if (data.getNome() != null) c.setNome(data.getNome());
        if (data.getEmail() != null) c.setEmail(data.getEmail());
        if (data.getTelefone() != null) c.setTelefone(data.getTelefone());
        if (data.getEndereco() != null) c.setEndereco(data.getEndereco());
        return repo.save(c);
    }

    @Transactional
    public void inativar(Long id) {
        Cliente c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        c.setAtivo(false);
        repo.save(c);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return repo.findByNomeContainingIgnoreCaseAndAtivoTrue(nome);
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return repo.findByEmailIgnoreCaseAndAtivoTrue(email);
    }
}
