package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.dto.request.ClienteRequestDTO;
import com.deliverytech.delivery_api.dto.response.ClienteResponseDTO;

import java.util.*;


public interface ClienteService {
   ClienteResponseDTO cadastrar(ClienteRequestDTO dto);

    ClienteResponseDTO buscarPorId(Long id);

    ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto);

    ClienteResponseDTO ativarDesativar(Long id);

    List<ClienteResponseDTO> listarAtivos();

    List<ClienteResponseDTO> buscarPorNome(String nome);
}
