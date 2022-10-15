package wns.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wns.constants.ClassificationProject;
import wns.constants.Filter;
import wns.constants.Messages;
import wns.dto.IdsDTO;
import wns.dto.ClientDTO;
import wns.dto.ProjectDTO;
import wns.entity.*;
import wns.repo.ProjectRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProjectService implements MainService {
    private final ProjectRepo projectRepo;
    private final ToolsService toolsService;
    private final ClientsService clientsService;
    private final ModelMapper modelMapper;
    private final PageableService pageableService;
    private final EstimateService estimateService;
    private final ToolsEstimateService toolsEstimateService;

    @Override
    public List<Project> getAll() {
        return projectRepo.findAll();
    }

    public Project createProject(ProjectDTO projectDTO) {
        Project project = projectRepo.findByName(projectDTO.getName());
        if (project == null) {
            try {
                project = projectDTO.createProjectFromDTO(projectDTO);
                ClientDTO client_dto = clientsService.findDTOById(projectDTO.getClient_id());
                client_dto.getProjects().add(project);
                project.setClient(modelMapper.map(client_dto, Client.class));
                project.setPhoneNumber(client_dto.getPhoneNumber());
                clientsService.saveClient(client_dto);
                Estimate estimate = estimateService.createEstimate(project);
                project.setEstimate(estimate);
                projectRepo.save(project);
                for (Long id : projectDTO.getTools_id()) {
                    Tools tool = modelMapper.map(toolsService.findById(id), Tools.class);
                    toolsService.addToolToProject(tool,project);
                    toolsEstimateService.addToolEstimateToEstimate(project,tool);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return project;
    }

    public void deleteProjectsByListsId(List<Long> ids) {
        for (Long id : ids) {
            Project project = projectRepo.findById(id).get();
            Set<Tools> tools = project.getTools();
            for (Tools tool : tools) {
                deleteToolFromProject(tool);
            }
            projectRepo.delete(project);
        }
    }

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


    public Messages addToolsToProject(long id, List<Long> list_id) {
        try {
            Project project = projectRepo.findById(id).get();
            for (Long id_tool : list_id) {
                Tools tool = modelMapper.map(toolsService.findById(id_tool), Tools.class);
                addToolToProject(tool, project);
            }
        } catch (Exception e) {
            return Messages.PROJECT_ERROR;
        }
        return Messages.PROJECT_UPDATE;
    }

    public Page<Project> findPaginated(Optional<Integer> page, Optional<Integer> size, String filter) {
        List<Project> listByFilter = getDataByFilter(filter);
        return pageableService.findPaginated(page, size, listByFilter);
    }

    public List<Project> getDataByFilter(String filter_string) {
        Filter filter = Filter.getFilterByString(filter_string);
        List<Project> list = new ArrayList<>();
        switch (filter) {
            case ONE_TIME -> list.addAll(projectRepo.findAllByClassification(ClassificationProject.ONE_TIME));
            case SUBLEASE -> list.addAll(projectRepo.findAllByClassification(ClassificationProject.SUBLEASE));
            case WITHOUT_FILTER -> list.addAll(projectRepo.findAll());
            case LONG -> list.addAll(projectRepo.findAllByClassification(ClassificationProject.LONG));
            case TEST -> list.addAll(projectRepo.findAllByClassification(ClassificationProject.TEST));
        }
        return list;
    }

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
            return Messages.REPLACE;
        } catch (Exception e) {
            e.printStackTrace();
            return Messages.PROJECT_ERROR;
        }
    }

    private void deleteToolFromProject(Tools tool) {
        toolsEstimateService.deleteToolEstimateFromEstimate(tool);
        toolsService.deleteToolFromProject(tool);
    }

    private void addToolToProject(Tools tools, Project project) {
        toolsService.addToolToProject(tools,project);
        toolsEstimateService.addToolEstimateToEstimate(project,tools);
    }
}
