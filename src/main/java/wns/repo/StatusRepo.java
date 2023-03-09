package wns.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import wns.constants.StatusTools;
import wns.entity.Status;

import java.util.List;

public interface StatusRepo extends PagingAndSortingRepository<Status, Long> {
    Status findByStatusTools(StatusTools statusTools);
    List<Status> findAllByStatusTools(StatusTools statusTools);
}
