package wns.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import wns.entity.RoleContact;

public interface RoleContactRepo extends PagingAndSortingRepository<RoleContact, Long> {
    RoleContact findByRole(String role);
}
