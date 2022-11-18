package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wns.aspects.ToLog;
import wns.constants.Messages;
import wns.constants.TypeClients;
import wns.dto.ClientDTO;
import wns.entity.Client;
import wns.entity.Owner;
import wns.repo.ClientsRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientsService implements MainService {
    private final ClientsRepo clientsRepo;

    public List<Client> getAll() {
        return (List<Client>) clientsRepo.findAll();
    }

    public List<ClientDTO> getAllClientsDTO()
    {
        List<Client> list = (List<Client>) clientsRepo.findAll();
        return list.stream().map(ClientDTO::new).collect(Collectors.toList());
    }
    @ToLog
    public Messages saveClient(Client client) {
        Client clientByFullName = clientsRepo.findClientByFullName(client.getFullName());
        if (clientByFullName == null) {
            clientsRepo.save(client);
            return Messages.CLIENT_CREATE;
        } else
            return Messages.CLIENT_EXISTS;
    }
    @ToLog
    public void updateClient(long id, Client client) {
        client.setId(id);
        clientsRepo.save(client);
    }

    public Client getById(long id) {
        return clientsRepo.findById(id).get();
    }

    @Override
    public void delete(long id) {
        clientsRepo.deleteById(id);
    }

    public List<ClientDTO> findListByName(String username) {
        return clientsRepo.findAllByFullNameContainingIgnoreCase(username).stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }

    public List<ClientDTO> findListByTypeClient(TypeClients typeClients) {
        return clientsRepo.findAllByTypeClient(typeClients).stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    public List<ClientDTO> findListByInBlackList(boolean in) {
        return clientsRepo.findAllByInBlackList(in).stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }
}
