package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import wns.constants.ClassificationProject;
import wns.constants.Messages;
import wns.constants.StatusProject;
import wns.dto.IdsDTO;
import wns.dto.ProjectDTO;
import wns.entity.*;
import wns.repo.ProjectRepo;

import java.util.*;
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

    @Override
    public List<Project> getAll() {
        return projectRepo.findAll();
    }

    public Project createProject(ProjectDTO projectDTO) {
        Project project = projectRepo.findByName(projectDTO.getName());
        if (project == null) {
            try {
                project = projectDTO.createProjectFromDTO(projectDTO);
                Client client = clientsService.getById(projectDTO.getClient_id());
                client.getProjects().add(project);
                project.setClient(client);
                project.setPhoneNumber(client.getPhoneNumber());
                clientsService.saveClient(client);
                Estimate estimate = estimateService.createEstimate(project);
                project.setEstimate(estimate);
                projectRepo.save(project);
                for (Long id : projectDTO.getItems()) {
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
            delete(project.getId());
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
                Tools tool = toolsService.findById(id_tool);
                addToolToProject(tool, project);
            }
        } catch (Exception e) {
            return Messages.PROJECT_ERROR;
        }
        return Messages.PROJECT_UPDATE;
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
            return Messages.REDIRECT;
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

    public List<ProjectDTO> findListByClassification(ClassificationProject classificationProject) {
        List<Project> list_project = projectRepo.findAllByClassification(classificationProject);
        return list_project.stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    public void updateProject(ProjectDTO dto) {
        Project project = projectRepo.findById(dto.getId()).get();
        project.setName(dto.getName());
        project.setNumber(dto.getNumber());
        project.setStatus(dto.getStatus());
        project.setTypeLease(dto.getTypeLease());
        project.setQuantity(dto.getQuantity());
        project.setCreated(dto.getCreated());
        project.setEmployee(SecurityContextHolder.getContext().getAuthentication().getName());
        project.setStart(dto.getStart());
        project.setEnd(dto.getEnd());
        project.setPhotos(dto.getPhotos());
        project.setDiscount(dto.getDiscount());
        project.setNote(dto.getNote());
        project.setSum(dto.getSum());
        project.setFinalSumUsn(dto.getFinalSumUsn());
        project.setPriceTools(dto.getPriceTools());
        project.setDiscountByProject(dto.getDiscountByProject());
        project.setSumWithDiscount(dto.getSumWithDiscount());
        project.setReceived(dto.getReceived());
        project.setRemainder(dto.getRemainder());
        project.setClassification(dto.getClassification());
        project.setPhoneNumber(dto.getPhoneNumber());
        project.setPriceWork(dto.getPriceWork());
        Client client = clientsService.getById(dto.getClient_id());
        client.getProjects().add(project);
        project.setClient(client);
        project.setPhoneNumber(client.getPhoneNumber());
        clientsService.saveClient(client);
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
