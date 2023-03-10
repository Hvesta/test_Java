package fr.omnilog.maref.service;

import fr.omnilog.maref.model.Client;
import fr.omnilog.maref.repository.ClientRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client getClientById(int clientId) {
        try {
            return clientRepository.getOne(clientId);
        } catch (EntityNotFoundException exception){
            throw new RuntimeException("Le client n'existe pas");
        }
    }
}
