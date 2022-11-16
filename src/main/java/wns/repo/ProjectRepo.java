package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.constants.ClassificationProject;
import wns.entity.Project;

import java.util.List;

public interface ProjectRepo extends CrudRepository<Project, Long> {
    Project findByName(String name);
    List<Project> findAllByClassification(ClassificationProject classificationProject);
}
