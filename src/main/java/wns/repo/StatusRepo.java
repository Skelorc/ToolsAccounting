package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.constants.StatusTools;
import wns.entity.Status;

import java.util.List;

public interface StatusRepo extends CrudRepository<Status, Long> {
    Status findByStatusTools(StatusTools statusTools);
    List<Status> findAllByStatusTools(StatusTools statusTools);
}
