package wns.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import wns.entity.Owner;

public interface OwnerRepo extends PagingAndSortingRepository<Owner, Long> {
    Owner findByNameIgnoreCase(String name);
}
