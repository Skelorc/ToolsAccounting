package wns.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import wns.constants.EstimateSection;
import wns.constants.StatusTools;
import wns.entity.Tools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ToolsRepo extends PagingAndSortingRepository<Tools,Long> {
    Optional<Tools> findByName(String name);
    Optional<Tools> findByBarcode(String barcode);
    List<Tools> findAllByStatus_StatusTools(StatusTools statusTools);
    List<Tools> findAllBySection(EstimateSection section);
    @Query("select t from Tools t where t.status.statusTools in ('ONLEASE','WAITING','BOOKING','REPAIR') and t.status.start = :start")
    List<Tools> findAllByStatusForCalendar(LocalDate start);
}
