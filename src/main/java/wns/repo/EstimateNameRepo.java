package wns.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import wns.entity.EstimateName;

public interface EstimateNameRepo extends PagingAndSortingRepository<EstimateName, Long> {
    EstimateName findByName(String name);
}
