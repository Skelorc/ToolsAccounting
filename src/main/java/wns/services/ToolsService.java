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

    public void updateStatus(List<IdentifiersStatus> statuses) {
        for (IdentifiersStatus identifiersStatus : statuses) {
            Tools tools = toolsRepo.findById(identifiersStatus.getId()).get();
            Status status = tools.getStatus();
            status.setEmployee(SecurityContextHolder.getContext().getAuthentication().getName());
            status.setStatusTools(StatusTools.valueOf(identifiersStatus.getStatus()));
            status.setCreated(LocalDateTime.now());
            statusService.save(status);
        }
    }

    public Messages changeStatus(TypeStatusDTO typeStatusDTO) {
        if (typeStatusDTO.getData() != null) {
            String[] data = typeStatusDTO.getData().split(",");
            typeStatusDTO.setExecutor(data[0]);
            typeStatusDTO.setPhoneNumber(data[1]);
        }
        List<Identifiers> identifiers = typeStatusDTO.getItems();
        for (Identifiers identifier : identifiers) {
            if (identifier.isChecked()) {
                if(identifier.getPrice()==0)
                {
                    return Messages.STATUS_CHANGE_FAILED;
                }
                Tools tools = toolsRepo.findById(identifier.getId()).get();
                Status status = tools.getStatus();
                status.setCreated(LocalDateTime.now());
                status.setStart(typeStatusDTO.getStart());
                status.setEnd(typeStatusDTO.getEnd());
                status.setEmployee(SecurityContextHolder.getContext().getAuthentication().getName());
                status.setExecutor(typeStatusDTO.getExecutor());
                status.setPhone_number(typeStatusDTO.getPhoneNumber());
                status.setNote(typeStatusDTO.getNote());
                status.setStatusTools(typeStatusDTO.getStatusTools());
                status.setPhotos(typeStatusDTO.getPhotos());
                if (status.getStatusTools().equals(StatusTools.SALE)) {
                    status.setPriceSell(status.getPriceSell() + identifier.getPrice());
                }
                if (status.getStatusTools().equals(StatusTools.REPAIR)) {
                    status.setPriceRepair(status.getPriceRepair() + identifier.getPrice());
                }
                if (status.getStatusTools().equals(StatusTools.WRITENOFF)) {
                    status.setPriceOff(status.getPriceOff() + identifier.getPrice());
                }
                try {
                    statusService.save(status);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Messages.STATUS_CHANGE_FAILED;
                }
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

    public List<ToolsDTO> findListByTypeTools(TypeTools typeTools) {
        return toolsRepo.findAllByTypeTools(typeTools).stream().map(ToolsDTO::new).collect(Collectors.toList());
    }

}
