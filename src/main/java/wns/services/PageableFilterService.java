package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import wns.constants.*;
import wns.dto.ClientDTO;
import wns.dto.PageDataDTO;
import wns.dto.ProjectDTO;
import wns.dto.ToolsDTO;
import wns.entity.Project;
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
    private final EstimateService estimateService;
    private final ClientsService clientsService;

    public Page<Object> getPageByFilter(PageDataDTO pageDataDTO) {
        List<Object> list_data = new ArrayList<>();
        getDataByFilter(pageDataDTO.getFilter(), pageDataDTO.getPaginationConst(), pageDataDTO.getId(), list_data);
        return findPaginated(pageDataDTO.getPage(), pageDataDTO.getSize(), list_data);
    }

    public Page<Object> getPageByFilter(Optional<Integer> page,
                                        Optional<Integer> size,
                                        Filter filter,
                                        PaginationConst paginationConst,
                                        long id) {
        List<Object> list_data = new ArrayList<>();
        getDataByFilter(filter, paginationConst, id, list_data);
        return findPaginated(page, size, list_data);
    }


    private void getDataByFilter(Filter filter, PaginationConst paginationConst, long id, List<Object> list) {
        if (filter == null)
            filter = Filter.WITHOUT_FILTER;
        switch (filter) {
            case ONE_TIME:
                list.addAll(projectService.findListByClassification(ClassificationProject.ONE_TIME));
                break;
            case SUBLEASE_PROJECT:
                list.addAll(projectService.findListByClassification(ClassificationProject.SUBLEASE_PROJECT));
                break;
            case LONG:
                list.addAll(projectService.findListByClassification(ClassificationProject.LONG));
                break;
            case TEST:
                list.addAll(projectService.findListByClassification(ClassificationProject.TEST));
                break;
            case GET_TOOLS_BY_PROJECT:
                Project byId = projectService.getById(id);
                Set<Tools> tools = byId.getTools();
                list.addAll(tools.stream().map(ToolsDTO::new).collect(Collectors.toList()));
                break;
            case STOCK:
                list.addAll(toolsService.findListByTypeToolsAndProject(TypeTools.STOCK,id));
                break;
            case SUBLEASE_TOOLS:
                list.addAll(toolsService.findListByTypeToolsAndProject(TypeTools.SUBLEASE_TOOLS,id));
                break;
            case INSTOCK:
                list.addAll(toolsService.getToolsByStatuses(StatusTools.INSTOCK));
                break;
            case WAITING:
                list.addAll(toolsService.getToolsByStatuses(StatusTools.WAITING));
                break;
            case ONLEASE:
                list.addAll(toolsService.getToolsByStatusesAndNotProject(StatusTools.ONLEASE, id));
                break;
            case REPAIR:
                list.addAll(statusService.getStatusesByFilter(StatusTools.REPAIR));
                break;
            case SALE:
                list.addAll(statusService.getStatusesByFilter(StatusTools.SALE));
                break;
            case WRITEOFF:
                list.addAll(statusService.getStatusesByFilter(StatusTools.WRITEOFF));
                break;
            case LEGAL:
                list.addAll(clientsService.findListByTypeClient(TypeClients.LEGAL));
                break;
            case INDIVIDUAL:
                list.addAll(clientsService.findListByTypeClient(TypeClients.INDIVIDUAL));
                break;
            case BLACKLIST:
                list.addAll(clientsService.findListByInBlackList(true));
                break;
            case ALL_CLIENTS:
                list.addAll(clientsService.getAllClientsDTO());
                break;
            case PROJECTS_BY_CLIENTS:
                list.addAll(clientsService.getById(id).getProjects().stream().map(ProjectDTO::new).collect(Collectors.toList()));
                break;
            case WITHOUT_FILTER:
                if (paginationConst.equals(PaginationConst.PROJECT)) {
                    list.addAll(projectService.getAll()
                            .stream()
                            .map(ProjectDTO::new)
                            .collect(Collectors.toList()));
                } else if (paginationConst.equals(PaginationConst.TOOLS))
                    list.addAll(toolsService.getAll().stream()
                            .map(ToolsDTO::new)
                            .collect(Collectors.toList()));
                else if (paginationConst.equals(PaginationConst.CLIENT))
                    list.addAll(clientsService.getAll().stream()
                            .map(ClientDTO::new)
                            .collect(Collectors.toList()));
                break;

        }
    }

    public <T> PageImpl<T> findPaginated(Optional<Integer> page, Optional<Integer> size,
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

    public <T> void addPageNumbersToModel(Page<T> list, Model model) {
        int totalPages = list.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
    }
}
