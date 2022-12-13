package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.entity.RoleContact;

public interface RoleContactRepo extends CrudRepository<RoleContact, Long> {
    RoleContact findByRole(String role);
}
