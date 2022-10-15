package wns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import wns.entity.Estimate;
import wns.entity.EstimateName;

public interface EstimateRepo extends JpaRepository<Estimate, Long> {
    Estimate findByProject(long id);
}
