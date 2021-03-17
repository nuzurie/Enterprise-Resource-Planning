package com.soen390.erp.inventory.service;

import com.soen390.erp.inventory.exceptions.InvalidClientOrderException;
import com.soen390.erp.inventory.model.ClientOrder;
import com.soen390.erp.inventory.repository.ClientOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientOrderService {

    private final ClientOrderRepository clientOrderRepository;

    public ClientOrderService(ClientOrderRepository clientOrderRepository) {
        this.clientOrderRepository = clientOrderRepository;
    }

    public List<ClientOrder> findAllClientOrders() {

        return clientOrderRepository.findAll();
    }

    public ClientOrder addClientOrder(ClientOrder clientOrder) throws InvalidClientOrderException {
        ClientOrder co = clientOrderRepository.save(clientOrder);

        if (co == null) throw new InvalidClientOrderException();

        return co;
    }
}
