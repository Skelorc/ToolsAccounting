package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import wns.constants.DateCalendar;
import wns.constants.Messages;
import wns.constants.StatusTools;
import wns.constants.TypeTools;
import wns.dto.*;
import wns.entity.*;
import wns.repo.ToolsRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToolsService implements MainService {
    private final ToolsRepo toolsRepo;
    private final EstimateNameService estimateNameService;
    private final CategoryService categoryService;
    private final OwnerService ownerService;
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

    public Messages createTools(Tools tools, EstimateName estimateName, StatusTools statusTools, Category category, Owner owner) {
        Tools toolToSave = toolsRepo.findByName(tools.getName()).orElse(null);
        if (toolToSave == null) {
            tools.setStatus(StatusToolDTO.createStatusWithTools(tools, statusTools));
            tools.setAmount(1);
            tools.setCategory(category);
            category.setNumberTool(category.getNumberTool()+1);
            tools.setOwner(owner);
            tools.setEstimateName(estimateName);
            toolsRepo.save(tools);
            categoryService.save(category);
            return Messages.TOOLS_CREATE;
        } else
            return Messages.TOOLS_EXISTS;
    }

    public void updateTool(Tools tools)
    {
        toolsRepo.save(tools);
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

    public List<ToolsDTO> findListByStatusAndProject(StatusTools statusTools, long id)
    {
        if(id>=0)
        {
            return toolsRepo.findAllByStatus_StatusTools(statusTools).stream()
                    .filter(x -> (x.getProject()==null || x.getProject().getId()!=id))
                    .map(ToolsDTO::new)
                    .collect(Collectors.toList());
        }
        return toolsRepo.findAllByStatus_StatusTools(statusTools).stream().map(ToolsDTO::new).collect(Collectors.toList());
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

    @Override
    public void delete(long id) {
        toolsRepo.deleteById(id);
    }


}
