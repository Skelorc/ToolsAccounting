package wns.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import wns.constants.TypeClients;
import wns.entity.Client;

import java.util.List;

public interface ClientsRepo extends PagingAndSortingRepository<Client, Long> {
    Client findClientByFullName(String fullName);
    List<Client> findAllByTypeClient(TypeClients type);
    List<Client> findAllByInBlackList(boolean isInBlackList);
    List<Client> findAllByFullNameContainingIgnoreCase(String username);
}
