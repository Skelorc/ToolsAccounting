package wns.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import wns.entity.ToolsEstimate;

public interface ToolsEstimateRepo extends PagingAndSortingRepository<ToolsEstimate,Long> {
    ToolsEstimate findByName(String name);
}
