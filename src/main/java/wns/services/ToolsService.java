package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import wns.constants.EstimateSection;
import wns.constants.Messages;
import wns.constants.StatusTools;
import wns.constants.TypeTools;
import wns.dto.*;
import wns.entity.EstimateName;
import wns.entity.Project;
import wns.entity.Status;
import wns.entity.Tools;
import wns.repo.ToolsRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToolsService implements MainService {
    private final ToolsRepo toolsRepo;
    private final EstimateNameService estimateNameService;
    private final StatusService statusService;

    public List<Tools> getAll() {
        return toolsRepo.findAll();
    }

    public List<Tools> getListToolsByStatus(StatusTools statusTools) {
        List<Status> listByStatuses = statusService.getListByStatuses(statusTools);
        return listByStatuses.stream()
                .filter(x -> x.getStatusTools().equals(statusTools))
                .map(Status::getTools)
                .collect(Collectors.toList());
    }

    public Messages createTools(Tools tools, long name_estimate_id, EstimateSection section, StatusTools status_tool) {
        Tools toolToSave = toolsRepo.findByName(tools.getName()).orElse(null);
        if (toolToSave == null) {
            EstimateName estimateName = estimateNameService.getNameEstimateById(name_estimate_id);
            tools.setStatus(StatusToolDTO.createStatusWithTools(tools, status_tool));
            tools.setSection(section);
            tools.setAmount(1);
            tools.setCategory(estimateName.getCategoryTools());
            tools.setEstimateName(estimateName);
            estimateName.getListTools().add(tools);
            toolsRepo.save(tools);
            estimateNameService.save(estimateName);
            return Messages.TOOLS_CREATE;
        } else
            return Messages.TOOLS_EXISTS;
    }

    public Messages changeStatus(StatusToolDTO statusToolDTO) {
        if (statusToolDTO.getData() != null) {
            String[] data = statusToolDTO.getData().split(",");
            statusToolDTO.setExecutor(data[0]);
            statusToolDTO.setPhone_number(data[1]);
        }
        List<Identifiers> identifiers = statusToolDTO.getItems();
        for (Identifiers identifier : identifiers) {
                Tools tools = toolsRepo.findById(identifier.getId()).get();
                Status status = tools.getStatus();
                status.setCreated(LocalDateTime.now());
                status.setStart(statusToolDTO.getStart());
                status.setEnd(statusToolDTO.getEnd());
                status.setEmployee(SecurityContextHolder.getContext().getAuthentication().getName());
                status.setExecutor(statusToolDTO.getExecutor());
                status.setPhone_number(statusToolDTO.getPhone_number());
                status.setNote(statusToolDTO.getNote());
                status.setStatusTools(statusToolDTO.getStatusTools());
                status.setPhotos(statusToolDTO.getPhotos());
                if (status.getStatusTools().equals(StatusTools.SALE)) {
                    status.setPriceSell(status.getPriceSell() + identifier.getPrice());
                }
                if (status.getStatusTools().equals(StatusTools.REPAIR)) {
                    status.setPriceRepair(status.getPriceRepair() + identifier.getPrice());
                }
                if (status.getStatusTools().equals(StatusTools.WRITEOFF)) {
                    status.setPriceOff(status.getPriceOff() + identifier.getPrice());
                }
                try {
                    statusService.save(status);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Messages.STATUS_CHANGE_FAILED;
                }
        }
        return Messages.STATUS_CREATE;
    }

    public Tools findById(long id) {
        return toolsRepo.findById(id).get();
    }

    public Tools getById(long id) {
        return toolsRepo.findById(id).get();
    }

    public void save(Tools tool) {
        Status status = tool.getStatus();
        toolsRepo.save(tool);
        statusService.save(status);
    }



    public void deleteToolFromProject(Tools tool)
    {
        tool.setProject(null);
        Status status = tool.getStatus();
        status.setCreated(LocalDateTime.now());
        status.setStatusTools(StatusTools.INSTOCK);
        status.setPhotos(tool.getPhotos());
        toolsRepo.save(tool);
    }

    public void addToolToProject(Tools tool,Project project)
    {
        Status status = StatusToolDTO.createStatusWithTools(tool, StatusTools.ONLEASE);
        status.setCreated(project.getCreated());
        status.setStart(project.getStart());
        status.setEnd(project.getEnd());
        status.setEmployee(SecurityContextHolder.getContext().getAuthentication().getName());
        status.setExecutor(project.getClient().getFullName());
        status.setNote(project.getNote());
        status.setPhone_number(project.getClient().getPhoneNumber());
        status.setPhotos(project.getPhotos());
        project.setSum(tool.getCostPrice() + project.getSum());
        tool.setProject(project);
        tool.setStatus(status);
        toolsRepo.save(tool);
    }

    public List<ToolsDTO> findListByTypeToolsAndProject(TypeTools typeTools, long id)
    {
        if(id>=0)
        {
            return toolsRepo.findAllByTypeTools(typeTools).stream()
                    .filter(x -> (x.getProject()==null || x.getProject().getId()!=id))
                    .map(ToolsDTO::new)
                    .collect(Collectors.toList());
        }
        return toolsRepo.findAllByTypeTools(typeTools).stream().map(ToolsDTO::new).collect(Collectors.toList());
    }

    public List<ToolsDTO> getToolsByStatuses(StatusTools statusTools)
    {
        return toolsRepo.findAllByStatus_StatusTools(statusTools)
                .stream()
                .map(ToolsDTO::new)
                .collect(Collectors.toList());
    }

    public List<ToolsDTO> getToolsByStatusesAndNotProject(StatusTools statusTools, long id)
    {
        List<Tools> allByStatusTools = toolsRepo.findAllByStatus_StatusTools(statusTools);
        return allByStatusTools.stream()
                .filter(x -> (x.getProject()!=null && x.getProject().getId()!=id))
                .map(ToolsDTO::new)
                .collect(Collectors.toList());
    }

}
