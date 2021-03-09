package fr.omnilog.maref.controller;

import fr.omnilog.maref.dto.ClientDTO;
import fr.omnilog.maref.model.Client;
import fr.omnilog.maref.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    private final ModelMapper modelMapper;

    public ClientController(ClientService clientService, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok(clients.stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList()));

    }
}
