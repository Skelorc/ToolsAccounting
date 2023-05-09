package wns.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import wns.constants.EstimateSection;
import wns.constants.StatusTools;
import wns.entity.Tools;

import java.util.List;
import java.util.Optional;

public interface ToolsRepo extends PagingAndSortingRepository<Tools,Long> {
    Optional<Tools> findByName(String name);
    Optional<Tools> findByBarcode(String barcode);
    List<Tools> findAllByStatus_StatusTools(StatusTools statusTools);
    List<Tools> findAllBySection(EstimateSection section);
    @Query("select t from Tools t where t.status.statusTools = 'ONLEASE' or t.status.statusTools = 'WAITING' or t.status.statusTools = 'BOOKING' or t.status.statusTools = 'REPAIR'")
    List<Tools> findAllByStatusForCalendar();
}
