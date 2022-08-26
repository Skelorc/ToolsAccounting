package wns.services;

import org.springframework.stereotype.Service;
import wns.constants.Answers;
import wns.entity.Client;
import wns.repo.ClientRepo;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepo clientRepo;
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    public List<Client> getAll() {
        return clientRepo.findAll();
    }

    public String saveClient(Client client) {
        Client clientByFullName = clientRepo.findClientByFullName(client.getFullName());
        if(clientByFullName==null)
        {
            clientRepo.save(client);
            return Answers.CLIENT_CREATE.getValue();
        }
        else
            return Answers.CLIENT_EXISTS.getValue();
    }
}
