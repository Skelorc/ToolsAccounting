package wns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import wns.entity.Tools;

public interface ToolsRepo extends JpaRepository<Tools,Long> {
    Tools findByName(String name);
}
