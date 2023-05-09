package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.aspects.ToLog;
import wns.constants.ClassificationProject;
import wns.constants.Filter;
import wns.constants.StatusProject;
import wns.dto.ProjectDTO;
import wns.entity.Client;
import wns.entity.Estimate;
import wns.entity.Project;
import wns.repo.ProjectRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ProjectService {
    private final ProjectRepo projectRepo;

    @Transactional(readOnly = true)
    public List<Project> getAll() {
        return (List<Project>) projectRepo.findAll();
    }

    @ToLog
    public Project createProject(ProjectDTO projectDTO, Client client, Estimate estimate) {
        Project project = projectRepo.findByName(projectDTO.getName());
        if (project == null) {
            try {
                project = projectDTO.createProjectFromDTO();
                project.setClient(client);
                project.setStart(projectDTO.getStart());
                project.setEnd(projectDTO.getEnd());
                project.setPhoneNumber(client.getPhoneNumber());
                project.setEstimate(estimate);
                projectRepo.save(project);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return project;
    }

    @Transactional(readOnly = true)
    public Project getById(long id) {
        return projectRepo.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<ProjectDTO> findListByClassification(ClassificationProject classificationProject) {
        List<Project> list_project = projectRepo.findAllByClassification(classificationProject);
        return list_project.stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    public void closeProject(ProjectDTO dto) {
        Project project = projectRepo.findById(dto.getId()).get();
        project.setStatus(StatusProject.CLOSED);
        projectRepo.save(project);
    }

    public void save(Project project) {
        projectRepo.save(project);
    }

    public void delete(long id) {
        projectRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Project getByName(String name) {
        return projectRepo.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<Project> findProjectsByDateShift(LocalDate localDate) {
        return projectRepo.findAllByWorkingShifts_DateShift(localDate);
    }

    @Transactional(readOnly = true)
    public List<ProjectDTO> findAllByDates(LocalDate startDate) {
        List<Project> projects = projectRepo.findAllForCalendar();
        return projects.stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

}
