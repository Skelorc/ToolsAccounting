package wns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import wns.entity.Project;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    Project findByName(String name);
}
