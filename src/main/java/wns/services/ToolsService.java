package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import wns.constants.*;
import wns.dto.*;
import wns.entity.EstimateName;
import wns.entity.Project;
import wns.entity.Status;
import wns.entity.Tools;
import wns.repo.ToolsRepo;

import javax.tools.Tool;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToolsService implements MainService {
    private final ToolsRepo toolsRepo;
    private final EstimateNameService estimateNameService;
    private final ModelMapper modelMapper;
    private final PageableService pageableService;
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

    public List<Tools> findByBarcode(String barcode) {
        List<Tools> list = new ArrayList<>();
        if (barcode != null) {
            Optional<Tools> tools = toolsRepo.findByBarcode(barcode);
            if (!tools.isEmpty())
                list.add(tools.get());
        }
        return list;
    }

    public Page<ToolsDTO> findPaginated(Optional<Integer> page, Optional<Integer> size, String filter) {
        List<Tools> listByFilter = getDataByFilter(filter, 0);
        List<ToolsDTO> collect = listByFilter.stream().map(ToolsDTO::new).collect(Collectors.toList());
        return pageableService.findPaginated(page, size, collect);
    }

    public Page<ToolsDTO> findPaginated(Optional<Integer> page, Optional<Integer> size, String filter, long id) {
        List<Tools> listByFilter = getDataByFilter(filter, id);
        List<ToolsDTO> collect = listByFilter.stream().map(ToolsDTO::new).collect(Collectors.toList());
        return pageableService.findPaginated(page, size, collect);
    }

    public Messages createTools(Tools toolsDTO, long name_estimate_id, EstimateSection section, StatusTools status_tool) {
        Tools toolToSave = toolsRepo.findByName(toolsDTO.getName()).orElse(null);
        if (toolToSave == null) {
            toolToSave = modelMapper.map(toolsDTO, Tools.class);
            EstimateName estimateName = estimateNameService.getNameEstimateById(name_estimate_id);
            toolToSave.setStatus(StatusToolDTO.createStatusWithTools(toolToSave, status_tool));
            toolToSave.setSection(section);
            toolToSave.setAmount(1);
            toolToSave.setCategory(estimateName.getCategoryTools());
            toolToSave.setEstimateName(estimateName);
            estimateName.getListTools().add(toolToSave);
            toolsRepo.save(toolToSave);
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
        List<Identifiers> identifiers = typeStatusDTO.getTools_id_with_prices();
        for (Identifiers identifier : identifiers) {
            if (identifier.isChecked()) {
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
                if (status.getStatusTools().equals(StatusTools.SALES)) {
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

    public List<Tools> getDataByFilter(String filter_string, long id) {
        Filter filter = Filter.getFilterByString(filter_string);
        List<Tools> list = new ArrayList<>();
        switch (filter) {
            case STOCK -> list.addAll(toolsRepo.findAllByTypeTools(TypeTools.STOCK));
            case SUBLEASE -> list.addAll(toolsRepo.findAllByTypeTools(TypeTools.SUBLEASE));
            case WITHOUT_FILTER -> list.addAll(toolsRepo.findAll());
            case INSTOCK -> list.addAll(statusService.getToolsByStatuses(StatusTools.INSTOCK));
            case ONLEASE -> list.addAll(statusService.getToolsByStatusesAndProject(StatusTools.ONLEASE, id));
        }
        return list;
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
        status.setStatusTools(StatusTools.ONLEASE);
        status.setEmployee(SecurityContextHolder.getContext().getAuthentication().getName());
        status.setExecutor(project.getClient().getFullName());
        status.setNote(project.getNote());
        status.setPhone_number(project.getClient().getPhoneNumber());
        status.setPhotos(project.getPhotos());
        project.setSum(tool.getCostPrice() + project.getSum());
        tool.setProject(project);
        toolsRepo.save(tool);
    }
}
