package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import wns.aspects.ToLog;
import wns.constants.*;
import wns.dto.*;
import wns.entity.Project;
import wns.entity.Status;
import wns.entity.Tools;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class PageableFilterService {

    private final ToolsService toolsService;
    private final ProjectService projectService;
    private final StatusService statusService;
    private final EstimateNameService estimateNameService;
    private final ClientsService clientsService;
    private final CategoryService categoryService;
    private final ContactsService contactsService;

    public Page<Object> getListData(PageDataDTO pageDataDTO) {
        List<Object> list = getDataByFilter(pageDataDTO.getFilter(), pageDataDTO.getId());
        return findPaginated(pageDataDTO.getPage(), pageDataDTO.getSize(),list);
    }


    @ToLog
    private List<Object> getDataByFilter(Filter filter, long id) {
        List<Object> list = new ArrayList<>();
        switch (filter) {
            case ONE_TIME -> list.addAll(projectService.findListByClassification(ClassificationProject.ONE_TIME));
            case SUBLEASE_PROJECT -> list.addAll(projectService.findListByClassification(ClassificationProject.SUBLEASE_PROJECT));
            case LONG -> list.addAll(projectService.findListByClassification(ClassificationProject.LONG));
            case TEST -> list.addAll(projectService.findListByClassification(ClassificationProject.TEST));
            case GET_TOOLS_BY_PROJECT -> {
                Project byId = projectService.getById(id);
                Set<Tools> tools = byId.getTools();
                list.addAll(tools.stream().map(ToolsDTO::new).collect(Collectors.toList()));
            }
            case STOCK -> list.addAll(toolsService.findListByStatusAndProject(StatusTools.INSTOCK, id));
            case INSTOCK -> list.addAll(toolsService.getToolsByStatuses(StatusTools.INSTOCK));
            case WAITING -> list.addAll(toolsService.getToolsByStatuses(StatusTools.WAITING));
            case ONLEASE -> list.addAll(toolsService.getToolsByStatusesAndNotProject(StatusTools.ONLEASE, id));
            case REPAIR -> list.addAll(statusService.getStatusesByFilter(StatusTools.REPAIR));
            case SALE -> list.addAll(statusService.getStatusesByFilter(StatusTools.SALE));
            case WRITEOFF -> list.addAll(statusService.getStatusesByFilter(StatusTools.WRITEOFF));
            case LEGAL -> list.addAll(clientsService.findListByTypeClient(TypeClients.LEGAL));
            case INDIVIDUAL -> list.addAll(clientsService.findListByTypeClient(TypeClients.INDIVIDUAL));
            case BLACKLIST -> list.addAll(clientsService.findListByInBlackList(true));
            case ALL_CLIENTS -> list.addAll(clientsService.getAllClientsDTO());
            case PROJECTS_BY_CLIENTS -> list.addAll(clientsService.getById(id).getProjects().stream().map(ProjectDTO::new).collect(Collectors.toList()));
            case ESTIMATE_NAME -> list.addAll(estimateNameService.getAll());
            case CATEGORY -> list.addAll(categoryService.getAll().stream().map(CategoryDTO::new).collect(Collectors.toList()));
            case ALL_PROJECTS -> list.addAll(projectService.getAll()
                    .stream()
                    .map(ProjectDTO::new)
                    .collect(Collectors.toList()));
            case ALL_CONTACTS -> list.addAll(contactsService.getAll()
                    .stream().map(ContactDTO::new).collect(Collectors.toList()));
            case ALL_TOOLS -> list.addAll(toolsService.getAll().stream().map(ToolsDTO::new).collect(Collectors.toList()));
        }
        return list;
    }

    private <T> PageImpl<T> findPaginated(Optional<Integer> page, Optional<Integer> size,
                                         List<T> list) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(100);
        int startItem = currentPage * pageSize;
        List<T> page_list;
        if (list.size() < startItem) {
            page_list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, list.size());
            page_list = list.subList(startItem, toIndex);
        }
        return new PageImpl<T>(page_list, PageRequest.of(currentPage, pageSize), list.size());
    }

    public <T> void addPageNumbers(Page<T> list, Model model) {
        int totalPages = list.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
    }
}
