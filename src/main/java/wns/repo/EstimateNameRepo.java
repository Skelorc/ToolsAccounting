package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.entity.EstimateName;

public interface EstimateNameRepo extends CrudRepository<EstimateName, Long> {
    EstimateName findByName(String name);
}
