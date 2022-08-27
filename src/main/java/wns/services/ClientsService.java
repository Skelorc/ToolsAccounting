package wns.services;

import org.springframework.stereotype.Service;
import wns.constants.Answers;
import wns.entity.Client;
import wns.repo.ClientsRepo;

import java.util.List;

@Service
public class ClientsService {
    private final ClientsRepo clientsRepo;
    public ClientsService(ClientsRepo clientsRepo) {
        this.clientsRepo = clientsRepo;
    }

    public List<Client> getAll() {
        return clientsRepo.findAll();
    }

    public String saveClient(Client client) {
        Client clientByFullName = clientsRepo.findClientByFullName(client.getFullName());
        if(clientByFullName==null)
        {
            clientsRepo.save(client);
            return Answers.CLIENT_CREATE.getValue();
        }
        else
            return Answers.CLIENT_EXISTS.getValue();
    }
}
