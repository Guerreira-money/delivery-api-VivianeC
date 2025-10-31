package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByAtivoTrue();
    List<Cliente> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);
    Optional<Cliente> findByEmailIgnoreCaseAndAtivoTrue(String email);
}
