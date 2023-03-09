package wns.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import wns.entity.WorkingShift;

public interface WorkingShiftRepo extends PagingAndSortingRepository<WorkingShift, Long> {
}
