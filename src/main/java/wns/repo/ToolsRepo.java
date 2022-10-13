package wns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wns.constants.CategoryTools;
import wns.constants.StatusTools;
import wns.constants.TypeTools;
import wns.entity.Tools;

import java.util.List;
import java.util.Optional;

public interface ToolsRepo extends JpaRepository<Tools,Long> {
    Optional<Tools> findByName(String name);
    List<Tools> findAllByTypeTools(TypeTools typeTools);
    Optional<Tools> findByBarcode(String barcode);
}
