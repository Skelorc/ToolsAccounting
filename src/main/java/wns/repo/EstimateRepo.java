package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.entity.Estimate;

public interface EstimateRepo extends CrudRepository<Estimate, Long> {
    Estimate findByProject(long id);
}
