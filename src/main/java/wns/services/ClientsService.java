package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wns.constants.Filter;
import wns.constants.Messages;
import wns.constants.TypeClients;
import wns.dto.ClientDTO;
import wns.entity.Client;
import wns.entity.User;
import wns.repo.ClientsRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static wns.constants.Filter.INDIVIDUAL;
import static wns.constants.Filter.LEGAL;

@Service
@AllArgsConstructor
public class ClientsService {
    private final ClientsRepo clientsRepo;
    private final ModelMapper modelMapper;


    public List<ClientDTO> getAll() {
        List<Client> list = clientsRepo.findAll();
        return createListDTO(list);
    }

    public Page<ClientDTO> findPaginated(Pageable pageable, String filter)
    {
        List<ClientDTO> listByFilter = getListByFilter(filter);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<ClientDTO> page_list;

        if(listByFilter.size() < startItem)
        {
         page_list = Collections.emptyList();
        }
        else
        {
            int toIndex = Math.min(startItem + pageSize, listByFilter.size());
            page_list = listByFilter.subList(startItem, toIndex);
        }
        return new PageImpl<>(page_list, PageRequest.of(currentPage, pageSize), listByFilter.size());
    }

    public List<ClientDTO> getListByFilter(String filter_string)
    {
        Filter filter = Filter.getFilterByString(filter_string);
        List<Client> list = new ArrayList<>();
        switch (filter) {
            case WITHOUT_FILTER -> list = clientsRepo.findAll();
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

    public Messages updateClient(long id,ClientDTO dto) {
        Client clientFromDb = clientsRepo.findById(id).orElse(new Client());
        modelMapper.map(dto, clientFromDb);
        clientsRepo.save(clientFromDb);
        return Messages.CLIENT_UPDATE;
    }

    public ClientDTO findById(long id) {
        Client client = clientsRepo.findById(id).orElse(new Client());
        return modelMapper.map(client, ClientDTO.class);
    }

    public void delete(long id) {
        clientsRepo.deleteById(id);
    }

    private List<ClientDTO> createListDTO(List<Client> list)
    {
        return list.stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }

    public List<ClientDTO> findListByName(String username) {
       return createListDTO(clientsRepo.findAllByFullNameContainingIgnoreCase(username));
    }
}
