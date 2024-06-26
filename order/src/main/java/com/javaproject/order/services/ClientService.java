package com.javaproject.order.services;

import com.javaproject.order.dto.ClientDTO;
import com.javaproject.order.model.Client;

import java.util.List;

public interface ClientService {
    List<ClientDTO> findAll();
    public Client getClient(Long clientId);
    ClientDTO save(ClientDTO clientDTO);
    boolean deleteById (Long id);
    ClientDTO findById(Long id);
}
