package wns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import wns.entity.Client;

public interface ClientsRepo extends JpaRepository<Client, Long> {

    Client findClientByFullName(String fullName);
}
