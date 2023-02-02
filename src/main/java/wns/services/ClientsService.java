package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.aspects.ToLog;
import wns.constants.Messages;
import wns.constants.TypeClients;
import wns.dto.ClientDTO;
import wns.entity.Client;
import wns.repo.ClientsRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ClientsService{
    private final ClientsRepo clientsRepo;

    @Transactional(readOnly = true)
    public List<Client> getAll() {
        return (List<Client>) clientsRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<ClientDTO> getAllClientsDTO()
    {
        List<Client> list = (List<Client>) clientsRepo.findAll();
        return list.stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @ToLog
    public Messages saveClient(ClientDTO dto) {
        Client client = clientsRepo.findClientByFullName(dto.getFullName());
        if (client == null) {
            client = dto.createClient();
            clientsRepo.save(client);
            return Messages.OK;
        } else
            return Messages.CLIENT_EXISTS;
    }

    @ToLog
    public Messages updateClient(Client client) {
        try {
            clientsRepo.save(client);
            return Messages.CLIENT_UPDATE;
        }catch (Exception e)
        {
            return Messages.CLIENT_NOT_FOUND;
        }
    }

    @Transactional(readOnly = true)
    public Client getById(long id) {
        return clientsRepo.findById(id).get();
    }

    public void delete(long id) {
        clientsRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ClientDTO> findListByName(String username) {
        return clientsRepo.findAllByFullNameContainingIgnoreCase(username).stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClientDTO> findListByTypeClient(TypeClients typeClients) {
        return clientsRepo.findAllByTypeClient(typeClients).stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClientDTO> findListByInBlackList(boolean in) {
        return clientsRepo.findAllByInBlackList(in).stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }
}
