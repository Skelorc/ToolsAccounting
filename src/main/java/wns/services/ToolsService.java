package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.aspects.ToLog;
import wns.constants.EstimateSection;
import wns.constants.Filter;
import wns.constants.StatusTools;
import wns.dto.*;
import wns.entity.*;
import wns.repo.ToolsRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ToolsService {
    private final ToolsRepo toolsRepo;

    @Transactional(readOnly = true)
    public List<Tools> getAll() {
        return (List<Tools>) toolsRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Page<ToolsDTO> getAll(Pageable paging) {
        List<ToolsDTO> all = toolsRepo.findAll(paging).stream().map(ToolsDTO::new).collect(Collectors.toList());
        return new PageImpl<>(all);
    }


    @ToLog
    public void createTools(Tools tools, EstimateName estimateName, StatusTools statusTools, Category category, Owner owner) {
        Tools toolToSave = toolsRepo.findByName(tools.getName()).orElse(null);
        if (toolToSave == null) {
            tools.setStatus(StatusToolDTO.createStatusWithTools(tools, statusTools));
            tools.setAmount(1);
            tools.setCategory(category);
            category.setNumberTool(category.getNumberTool() + 1);
            tools.setOwner(owner);
            tools.setEstimateName(estimateName);
            toolsRepo.save(tools);
        }
    }

    public void updateTool(Tools tools) {
        toolsRepo.save(tools);
    }

    @Transactional(readOnly = true)
    @ToLog
    public List<Status> changeStatus(StatusToolDTO statusToolDTO) {
        if (statusToolDTO.getData() != null) {
            String[] data = statusToolDTO.getData().split(",");
            statusToolDTO.setExecutor(data[0]);
            statusToolDTO.setPhone_number(data[1]);
        }
        List<Identifiers> identifiers = statusToolDTO.getItems();
        List<Status> statusList = new ArrayList<>();
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
            statusList.add(status);
        }
        return statusList;
    }

    @Transactional(readOnly = true)
    public Tools findById(long id) {
        return toolsRepo.findById(id).get();
    }

    public void deleteToolFromProject(Tools tool) {
        tool.setProject(null);
        Status status = tool.getStatus();
        status.setCreated(LocalDateTime.now());
        status.setStatusTools(StatusTools.INSTOCK);
        status.setPhotos(tool.getPhotos());
        toolsRepo.save(tool);
    }

    public void addToolToProject(Tools tool, Project project) {
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

    @Transactional(readOnly = true)
    public List<ToolsDTO> findListByStatusAndProject(StatusTools statusTools, long id) {
        if (id >= 0) {
            return toolsRepo.findAllByStatus_StatusTools(statusTools).stream()
                    .filter(x -> (x.getProject() == null || x.getProject().getId() != id))
                    .map(ToolsDTO::new)
                    .collect(Collectors.toList());
        }
        return toolsRepo.findAllByStatus_StatusTools(statusTools).stream().map(ToolsDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ToolsDTO> getToolsByStatuses(StatusTools statusTools) {
        return toolsRepo.findAllByStatus_StatusTools(statusTools)
                .stream()
                .map(ToolsDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ToolsDTO> getToolsByStatusesAndNotProject(StatusTools statusTools, long id) {
        List<Tools> allByStatusTools = toolsRepo.findAllByStatus_StatusTools(statusTools);
        return allByStatusTools.stream()
                .filter(x -> (x.getProject() != null && x.getProject().getId() != id))
                .map(ToolsDTO::new)
                .collect(Collectors.toList());
    }

    public void delete(long id) {
        toolsRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Tools> getToolsBySection(String section) {
        return toolsRepo.findAllBySection(EstimateSection.valueOf(section));
    }

    @Transactional(readOnly = true)
    public List<CalendarDataDTO> findAllByFilter(LocalDate startDate, CalendarFilterDTO filterDTO) {
        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.of(0, 0));
        LocalDateTime end;
        if (filterDTO.getFilter().equals(Filter.TOOLS_BY_WEEK))
            end = LocalDateTime.of(startDate.plusWeeks(1), LocalTime.of(0, 0));
        else
            end = LocalDateTime.of(startDate.plusMonths(1), LocalTime.of(0, 0));
        List<Tools> listFromDb = toolsRepo.findAllByStatusForCalendar(start, end);
        List<Tools> listTools = filteringListTools(listFromDb, filterDTO.getFilters());
        CalendarDataDTO calendarToolDTO;
        List<CalendarDataDTO> listData = new ArrayList<>();
        for (Tools tool : listTools) {
            Status status = tool.getStatus();
            if (listData.isEmpty()) {
                calendarToolDTO = new CalendarDataDTO(tool.getName(), tool.getId(), tool.getStatus().getCreated());
                calendarToolDTO.addDataToList(status.getStart(), status.getEnd(), status.getStatusTools().toString());
                listData.add(calendarToolDTO);
            } else {
                Optional<CalendarDataDTO> calendarToolDTOOptional = listData.stream().filter(x -> x.getName().equals(tool.getName())).findAny();
                if (calendarToolDTOOptional.isEmpty()) {
                    calendarToolDTO = new CalendarDataDTO(tool.getName(), tool.getId(), tool.getStatus().getCreated());
                    calendarToolDTO.addDataToList(status.getStart(), status.getEnd(), status.getStatusTools().toString());
                    listData.add(calendarToolDTO);
                } else
                    calendarToolDTOOptional.get().addDataToList(status.getStart(), status.getEnd(), status.getStatusTools().toString());
            }
        }
        return listData;
    }


    private List<Tools> filteringListTools(List<Tools> listTools, FiltersDTO filters) {
        List<Tools> resultList = new ArrayList<>();
        for (Tools tool : listTools) {
            if (!filters.getClients().isEmpty() && !tool.getProject().getClient().getFullName().equals(filters.getClients()))
                continue;
            if (!filters.getManagers().isEmpty() && !tool.getProject().getEmployee().equals(filters.getManagers()))
                continue;
            if (!filters.getNames().isEmpty() && !tool.getName().equals(filters.getNames()))
                continue;
            if (!filters.getTypes().isEmpty() && !tool.getSection().toString().equals(filters.getTypes()))
                continue;
            resultList.add(tool);
        }
        return resultList;
    }

}
