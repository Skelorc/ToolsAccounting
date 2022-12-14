package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.aspects.ToLog;
import wns.constants.ClassificationProject;
import wns.constants.Messages;
import wns.constants.StatusProject;
import wns.dto.IdsDTO;
import wns.dto.ProjectDTO;
import wns.entity.*;
import wns.repo.ProjectRepo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService implements MainService {
    private final ProjectRepo projectRepo;
    private final ToolsService toolsService;
    private final ClientsService clientsService;
    private final ModelMapper modelMapper;
    private final EstimateService estimateService;
    private final ToolsEstimateService toolsEstimateService;
    private final WorkingShiftService workingShiftService;

    @Override
    public List<Project> getAll() {
        return (List<Project>) projectRepo.findAll();
    }

    @ToLog
    @Transactional
    public Project createProject(ProjectDTO projectDTO) {
        Project project = projectRepo.findByName(projectDTO.getName());
        if (project == null) {
            try {
                project = projectDTO.createProjectFromDTO();
                Client client = clientsService.getById(projectDTO.getClient_id());
                project.setClient(client);
                project.setPhoneNumber(client.getPhoneNumber());
                Estimate estimate = estimateService.createEstimate(project);
                estimate.setCount_shifts(projectDTO.getWorkingShifts().size());
                project.setEstimate(estimate);
                projectRepo.save(project);
                for (Long id : projectDTO.getItems()) {
                    Tools tool = modelMapper.map(toolsService.findById(id), Tools.class);
                    toolsService.addToolToProject(tool,project);
                    toolsEstimateService.addToolEstimateToEstimate(project,tool);
                }
                for (WorkingShift workingShift : projectDTO.getWorkingShifts()) {
                    workingShift.setProject(project);
                    workingShiftService.save(workingShift);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return project;
    }

    @ToLog
    public void deleteProjectsByListsId(List<Long> ids) {
        for (Long id : ids) {
            Project project = projectRepo.findById(id).get();
            Set<Tools> tools = project.getTools();
            for (Tools tool : tools) {
                deleteToolFromProject(tool);
            }
            for (WorkingShift workingShift : project.getWorkingShifts()) {
                workingShiftService.delete(workingShift.getId());
            }
            delete(project.getId());
        }
    }

    @ToLog
    public Messages clearToolsFromProject(List<Long> list_id_tools) {
        for (Long id_tool : list_id_tools) {
            Tools tool = toolsService.getById(id_tool);
            deleteToolFromProject(tool);
        }
        return Messages.DELETE;
    }

    public Project getById(long id) {
        return projectRepo.findById(id).get();
    }

    @ToLog
    public Messages addToolsToProject(long id, List<Long> list_id) {
        try {
            Project project = projectRepo.findById(id).get();
            for (Long id_tool : list_id) {
                Tools tool = toolsService.findById(id_tool);
                addToolToProject(tool, project);
            }
        } catch (Exception e) {
            return Messages.PROJECT_ERROR;
        }
        return Messages.PROJECT_UPDATE;
    }

    @ToLog
    public Messages changeToolsInProject(long id_project, IdsDTO ids) {
        try {
            Project project = projectRepo.findById(id_project).get();
            Set<Tools> tools = project.getTools();
            for (Tools old_tool : tools) {
                List<Long> old_ids = ids.getOld_ids();
                for (Long old_id : old_ids) {
                    if (old_tool.getId() == old_id) {
                        deleteToolFromProject(old_tool);
                    }
                }
            }
            for (Long new_id : ids.getNew_ids()) {
                Tools new_tool = toolsService.findById(new_id);
                addToolToProject(new_tool, project);
            }
            return Messages.REDIRECT;
        } catch (Exception e) {
            e.printStackTrace();
            return Messages.PROJECT_ERROR;
        }
    }

    @Transactional
    protected void deleteToolFromProject(Tools tool) {
        toolsEstimateService.deleteToolEstimateFromEstimate(tool);
        toolsService.deleteToolFromProject(tool);
    }

    @Transactional
    protected void addToolToProject(Tools tools, Project project) {
        toolsService.addToolToProject(tools,project);
        toolsEstimateService.addToolEstimateToEstimate(project,tools);
    }

    public List<ProjectDTO> findListByClassification(ClassificationProject classificationProject) {
        List<Project> list_project = projectRepo.findAllByClassification(classificationProject);
        return list_project.stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    @ToLog
    @Transactional
    public void updateProject(ProjectDTO dto) {
        Project project = dto.createProjectFromDTO();
        Client client = clientsService.getById(dto.getClient_id());
        client.getProjects().add(project);
        project.setClient(client);
        project.setPhoneNumber(client.getPhoneNumber());
        for (WorkingShift workingShift : dto.getWorkingShifts()) {
            workingShift.setProject(project);
            workingShiftService.save(workingShift);
        }
        clientsService.updateClient(client);
        projectRepo.save(project);
    }

    public void closeProject(ProjectDTO dto) {
        Project project = projectRepo.findById(dto.getId()).get();
        project.setStatus(StatusProject.CLOSED);
        projectRepo.save(project);
    }

    public void save(Project project) {
        projectRepo.save(project);
    }

    @Override
    public void delete(long id) {
        projectRepo.deleteById(id);
    }
}
