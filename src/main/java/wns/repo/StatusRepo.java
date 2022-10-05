package wns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import wns.constants.StatusTools;
import wns.entity.Status;

import java.util.List;

public interface StatusRepo extends JpaRepository<Status, Long> {
    Status findByStatusTools(StatusTools statusTools);
    List<Status> findAllByStatusTools(StatusTools statusTools);
}
