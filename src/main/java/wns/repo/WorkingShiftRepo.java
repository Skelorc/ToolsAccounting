package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.entity.WorkingShift;

public interface WorkingShiftRepo extends CrudRepository<WorkingShift, Long> {
}
