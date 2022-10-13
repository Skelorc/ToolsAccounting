package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wns.constants.Filter;
import wns.constants.Messages;
import wns.constants.TypeClients;
import wns.dto.ClientDTO;
import wns.entity.Client;
import wns.repo.ClientsRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientsService implements MainService {
    private final ClientsRepo clientsRepo;
    private final ModelMapper modelMapper;
    private final PageableService pageableService;

    public List<ClientDTO> getAll() {
        return createListDTO(clientsRepo.findAll());
    }

    public Page<ClientDTO> findPaginated(Optional<Integer> page, Optional<Integer> size, String filter) {
        List<ClientDTO> listByFilter = getListByFilter(filter);
        return pageableService.findPaginated(page, size, listByFilter);
    }

    public List<ClientDTO> getListByFilter(String filter_string) {
        Filter filter = Filter.getFilterByString(filter_string);
        List<Client> list = new ArrayList<>();
        switch (filter) {
            case WITHOUT_FILTER -> list.addAll(clientsRepo.findAll());
            case LEGAL -> list.addAll(clientsRepo.findAllByTypeClient(TypeClients.LEGAL));
            case INDIVIDUAL -> list.addAll(clientsRepo.findAllByTypeClient(TypeClients.INDIVIDUAL));
            case BLACKLIST -> list.addAll(clientsRepo.findAllByInBlackList(true));
        }
        return createListDTO(list);
    }

    public Messages saveClient(ClientDTO dto) {
        Client clientByFullName = clientsRepo.findClientByFullName(dto.getFullName());
        if (clientByFullName == null) {
            Client client = modelMapper.map(dto, Client.class);
            clientsRepo.save(client);
            return Messages.CLIENT_CREATE;
        } else
            return Messages.CLIENT_EXISTS;
    }

    public Messages updateClient(long id, ClientDTO dto) {
        Client clientFromDb = clientsRepo.findById(id).orElse(new Client());
        modelMapper.map(dto, clientFromDb);
        clientsRepo.save(clientFromDb);
        return Messages.CLIENT_UPDATE;
    }

    public ClientDTO findDTOById(long id) {
        Client client = clientsRepo.findById(id).orElse(new Client());
        return modelMapper.map(client, ClientDTO.class);
    }
    public Client getById(long id) {
        return clientsRepo.findById(id).get();
    }
    public void delete(long id) {
        clientsRepo.deleteById(id);
    }

    private List<ClientDTO> createListDTO(List<Client> list) {
        return list.stream()
                .map(x -> modelMapper.map(x, ClientDTO.class))
                .collect(Collectors.toList());
    }

    public List<ClientDTO> findListByName(String username) {
        return createListDTO(clientsRepo.findAllByFullNameContainingIgnoreCase(username));
    }

}
