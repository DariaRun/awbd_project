package com.javaproject.order.services;

import com.javaproject.order.dto.ClientDTO;
import com.javaproject.order.exceptions.ClientDoesNotExistException;
import com.javaproject.order.model.Client;
import com.javaproject.order.repositories.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    ClientRepository clientRepository;
    ModelMapper modelMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ClientDTO> findAll() {
        List<Client> clients = new LinkedList<>();
        clientRepository.findAll().iterator().forEachRemaining(clients::add);

        return clients.stream().map(client -> modelMapper.map(client, ClientDTO.class)).collect(Collectors.toList());
    }

    public Client getClient(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);

        if(client.isEmpty()) {
            throw new ClientDoesNotExistException();
        }

        return client.get();
    }

    @Override
    public ClientDTO findById(Long l) {
        Optional<Client> clientOptional = clientRepository.findById(l);
        if (!clientOptional.isPresent()) {
            throw new ClientDoesNotExistException();
            //throw new RuntimeException("Product not found!");
        }
        return modelMapper.map(clientOptional.get(), ClientDTO.class);
    }

    @Override
    public ClientDTO save (ClientDTO client) {
        Client savedClient = clientRepository.save(modelMapper.map(client, Client.class));
        return modelMapper.map(savedClient, ClientDTO.class);
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (!clientOptional.isPresent())
            throw new ClientDoesNotExistException();
        clientRepository.deleteById(id);
        return true;
    }
}
