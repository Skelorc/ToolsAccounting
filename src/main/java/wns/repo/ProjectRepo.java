package wns.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import wns.constants.ClassificationProject;
import wns.entity.Project;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepo extends PagingAndSortingRepository<Project, Long> {
    Project findByName(String name);
    List<Project> findAllByClassification(ClassificationProject classificationProject);
    List<Project> findAllByWorkingShifts_DateShift(LocalDate localDate);
    @Query("select p from Project as p where p.status != 'CLOSED'")
    List<Project> findAllForCalendar();
}
