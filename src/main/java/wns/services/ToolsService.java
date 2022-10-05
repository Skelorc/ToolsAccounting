package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import wns.constants.Filter;
import wns.constants.Messages;
import wns.constants.StatusTools;
import wns.constants.TypeTools;
import wns.dto.*;
import wns.entity.EstimateName;
import wns.entity.Status;
import wns.entity.Tools;
import wns.repo.ToolsRepo;

import java.time.LocalDateTime;
import java.util.*;

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
        List<Tools> list_tools = new ArrayList<>();
        List<Status> listByStatuses = statusService.getListByStatuses(statusTools);
        for (Status status : listByStatuses) {
            if (status.getStatusTools().equals(statusTools)) {
                list_tools.add(status.getTools());
            }
        }
        return list_tools;
    }

    public List<Tools> findByBarcode(String barcode)
    {
        List<Tools> list = new ArrayList<>();
        if(barcode!=null)
        {
            Optional<Tools> tools = toolsRepo.findByBarcode(barcode);
            if(!tools.isEmpty())
                list.add(tools.get());
        }
        return list;
    }

    public List<Tools> getListByFilter(String filter_string) {
        Filter filter = Filter.getFilterByString(filter_string);
        List<Tools> list = new ArrayList<>();
        switch (filter) {
            case STOCK -> list.addAll(toolsRepo.findAllByTypeTools(TypeTools.STOCK));
            case SUBLEASE -> list.addAll(toolsRepo.findAllByTypeTools(TypeTools.SUBLEASE));
            case WITHOUT_FILTER -> list.addAll(toolsRepo.findAll());
        }
        return list;
    }

    public Page<Tools> findPaginated(Optional<Integer> page, Optional<Integer> size, String filter) {
        List<Tools> listByFilter = getListByFilter(filter);
        return pageableService.findPaginated(page, size, listByFilter);
    }

    public Messages createTools(ToolsDTO toolsDTO, long name_estimate_id, StatusTools status_tool) {
        Tools toolToSave = toolsRepo.findByName(toolsDTO.getName()).orElse(null);
        if (toolToSave == null) {
            toolToSave = modelMapper.map(toolsDTO, Tools.class);
            EstimateName estimateName = estimateNameService.getNameEstimateById(name_estimate_id);
            toolToSave.setStatus(StatusToolDTO.createStatusWithTools(toolToSave, status_tool));
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
            if(identifier.isChecked()) {
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

    public ToolsDTO findById(long id) {
        return modelMapper.map(toolsRepo.findById(id).get(), ToolsDTO.class);
    }

    public void save(Tools tool) {
        Status status = tool.getStatus();
        statusService.save(status);
        toolsRepo.save(tool);
    }
}
