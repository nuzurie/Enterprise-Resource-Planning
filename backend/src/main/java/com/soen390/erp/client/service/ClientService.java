package com.soen390.erp.client.service;

import com.soen390.erp.client.exception.ClientNotFoundException;
import com.soen390.erp.client.model.Client;
import com.soen390.erp.client.repository.ClientRepository;
import com.soen390.erp.inventory.exceptions.InvalidClientOrderException;
import com.soen390.erp.inventory.model.ClientOrder;
import com.soen390.erp.manufacturing.exceptions.BikeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public Client findClientById(int id) throws ClientNotFoundException{
        Client c = clientRepository.findById(id);

        if (c == null) throw new ClientNotFoundException(id);

        return c;
    }


    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }


    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }
}
