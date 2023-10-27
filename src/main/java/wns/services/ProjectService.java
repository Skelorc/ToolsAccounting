package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.aspects.ToLog;
import wns.constants.ClassificationProject;
import wns.constants.StatusProject;
import wns.dto.CalendarDataDTO;
import wns.dto.FiltersDTO;
import wns.dto.ProjectDTO;
import wns.entity.Client;
import wns.entity.Estimate;
import wns.entity.Project;
import wns.entity.Tools;
import wns.repo.ProjectRepo;

import java.time.LocalDate;
import java.util.*;
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
    public List<CalendarDataDTO> findAllByFilter(FiltersDTO filtersDTO) {
        List<Project> allProjects = projectRepo.findAllForCalendar();
        List<Project> list_project = filteringListProject(allProjects,filtersDTO);
        List<CalendarDataDTO> listData = new ArrayList<>();
        CalendarDataDTO calendarToolDTO;
        for (Project project : list_project) {
            if (listData.isEmpty()) {
                calendarToolDTO = new CalendarDataDTO(project.getName(), project.getId(), project.getCreated());
                calendarToolDTO.addDataToList(project.getStart(), project.getEnd(), project.getStatus().toString());
                listData.add(calendarToolDTO);
            } else {
                Optional<CalendarDataDTO> calendarToolDTOOptional = listData.stream().filter(x -> x.getName().equals(project.getName())).findAny();
                if (calendarToolDTOOptional.isEmpty()) {
                    calendarToolDTO = new CalendarDataDTO(project.getName(), project.getId(), project.getCreated());
                    calendarToolDTO.addDataToList(project.getStart(), project.getEnd(), project.getStatus().toString());
                    listData.add(calendarToolDTO);
                } else {
                    calendarToolDTOOptional.get().addDataToList(project.getStart(), project.getEnd(), project.getStatus().toString());
                }
            }
        }
        return listData;
    }

    private List<Project> filteringListProject(List<Project> projectsList, FiltersDTO filters) {
        List<Project> resultList = new ArrayList<>();
        for (Project project : projectsList) {
            if (!filters.getClients().isEmpty() && project.getClient().getFullName().equals(filters.getClients()))
                break;
            if (!filters.getManagers().isEmpty() && project.getEmployee().equals(filters.getManagers()))
                break;
            if (!filters.getNames().isEmpty() && project.getName().equals(filters.getNames()))
                break;
            if (!filters.getTypes().isEmpty() && project.getClassification().toString().equals(filters.getTypes()))
                break;
            resultList.add(project);
        }
        return resultList;
    }


}
