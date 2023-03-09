package wns.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import wns.entity.Estimate;

public interface EstimateRepo extends PagingAndSortingRepository<Estimate, Long> {
    Estimate findByProject(long id);
}
