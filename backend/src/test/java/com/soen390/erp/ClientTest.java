package com.soen390.erp;

import com.soen390.erp.accounting.controller.ClientController;
import com.soen390.erp.accounting.model.Client;
import com.soen390.erp.accounting.service.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    ClientService clientService;

    @Before
    public void setup()
    {
        clientController = new ClientController(clientService);
    }


    @Test
    public void getAllClientsTest()
    {
        Client c1 = new Client();

        List<Client> clients = new ArrayList<>();
        clients.add(c1);

        when(clientService.findAllClients()).thenReturn(clients);

        List<Client> result = clientController.getAllClients();

        assertThat(result.size()).isEqualTo(1);
    }


    @Test
    public void findClientByIdTest()
    {
        int id = 1;
        Client s1 = new Client();
        doReturn(s1).when(clientService).findClientById(id);

        ResponseEntity<?> result = clientController.findClientById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void addClientTest()
    {
        Client s1 = new Client();
        doReturn(s1).when(clientService).saveClient(s1);

        ResponseEntity<?> result = clientController.addClient(s1);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void deleteClientByIdTest()
    {
        int id = 1;
        doReturn(true).when(clientService).deleteClientById(id);

        ResponseEntity<?> result = clientController.deleteClientById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }


}
