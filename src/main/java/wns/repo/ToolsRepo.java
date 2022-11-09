package wns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import wns.constants.StatusTools;
import wns.entity.Tools;

import java.util.List;
import java.util.Optional;

public interface ToolsRepo extends JpaRepository<Tools,Long> {
    Optional<Tools> findByName(String name);
    Optional<Tools> findByBarcode(String barcode);
    List<Tools> findAllByStatus_StatusTools(StatusTools statusTools);
}
