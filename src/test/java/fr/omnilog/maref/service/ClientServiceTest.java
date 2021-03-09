package fr.omnilog.maref.service;

import fr.omnilog.maref.model.Client;
import fr.omnilog.maref.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    public void before(){
        client = new Client();
        client.setId(1);
        client.setName("Test client");

        Mockito.when(clientRepository.findAll()).thenReturn(List.of(client));
    }

    @Test
    public void findAllTest(){
        assertIterableEquals(List.of(client), clientService.findAll() );
    }

}