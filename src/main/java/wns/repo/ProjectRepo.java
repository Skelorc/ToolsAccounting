package wns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import wns.constants.ClassificationProject;
import wns.entity.Project;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    Project findByName(String name);
    List<Project> findAllByClassification(ClassificationProject classificationProject);
}
