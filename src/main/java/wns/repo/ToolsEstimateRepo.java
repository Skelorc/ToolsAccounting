package wns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import wns.entity.ToolsEstimate;

public interface ToolsEstimateRepo extends JpaRepository<ToolsEstimate,Long> {
    ToolsEstimate findByName(String name);
}
