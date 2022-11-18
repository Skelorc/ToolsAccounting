package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.constants.StatusTools;
import wns.entity.Tools;

import java.util.List;
import java.util.Optional;

public interface ToolsRepo extends CrudRepository<Tools,Long> {
    Optional<Tools> findByName(String name);
    Optional<Tools> findByBarcode(String barcode);
    List<Tools> findAllByStatus_StatusTools(StatusTools statusTools);
}
