package wns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import wns.entity.EstimateName;

public interface EstimateNameRepo extends JpaRepository<EstimateName, Long> {
    EstimateName findByName(String name);
}
