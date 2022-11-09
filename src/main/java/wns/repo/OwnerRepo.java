package wns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import wns.entity.Owner;

public interface OwnerRepo extends JpaRepository<Owner, Long> {
    Owner findByNameIgnoreCase(String name);
}
