package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.entity.ToolsEstimate;

public interface ToolsEstimateRepo extends CrudRepository<ToolsEstimate,Long> {
    ToolsEstimate findByName(String name);
}
