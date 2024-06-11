package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    public static final String CLIENT_NAME = "NAME";
    public static final Long EXIST_CLIENT_ID = 1L;
    public static final Long NOT_EXIST_CLIENT_ID = 10L;

    @Test
    public void findAllShouldReturnAllClients() {
        List<Client> list = new ArrayList<>();
        list.add(mock(Client.class));

        when(clientRepository.findAll()).thenReturn(list);

        List<Client> clients = clientService.findAll();

        assertNotNull(clients);
        assertEquals(1, clients.size());
    }

    @Test
    public void saveNotExistClientIdShouldInsert() throws Exception {
        ClientDto dto = new ClientDto();
        dto.setName(CLIENT_NAME);

        ArgumentCaptor<Client> client = ArgumentCaptor.forClass(Client.class);

        clientService.save(null, dto);

        verify(clientRepository).save(client.capture());

        assertEquals(CLIENT_NAME, client.getValue().getName());
    }

    @Test
    public void deleteExistsClientIdShouldDelete() throws Exception {
        Client client = new Client();
        when(clientRepository.findById(EXIST_CLIENT_ID)).thenReturn(Optional.of(client));

        clientService.delete(EXIST_CLIENT_ID);

        verify(clientRepository).deleteById(EXIST_CLIENT_ID);
    }

    @Test
    public void getExistClientShouldReturnClient() {
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(EXIST_CLIENT_ID);
        when(clientRepository.findById(EXIST_CLIENT_ID)).thenReturn(Optional.of(client));

        Client clientResponse = clientService.get(EXIST_CLIENT_ID);

        assertNotNull(clientResponse);
        assertEquals(EXIST_CLIENT_ID, clientResponse.getId());
    }

    @Test
    public void getNotExistClientIdShouldReturnNull() {
        when(clientRepository.findById(NOT_EXIST_CLIENT_ID)).thenReturn(Optional.empty());

        Client client = clientService.get(NOT_EXIST_CLIENT_ID);

        assertNull(client);
    }
}
