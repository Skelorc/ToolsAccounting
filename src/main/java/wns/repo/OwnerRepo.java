package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.entity.Owner;

public interface OwnerRepo extends CrudRepository<Owner, Long> {
    Owner findByNameIgnoreCase(String name);
}
