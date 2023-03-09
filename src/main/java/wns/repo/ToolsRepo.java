package wns.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import wns.constants.CategoryTools;
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
}
