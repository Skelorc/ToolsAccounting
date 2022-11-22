package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.entity.RoleClient;

public interface RoleClientRepo extends CrudRepository<RoleClient, Long> {
    RoleClient findByRole(String role);
}
