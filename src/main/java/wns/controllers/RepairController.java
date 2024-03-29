package wns.controllers;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Filter;
import wns.constants.Messages;
import wns.constants.StatusTools;
import wns.dto.PageDataDTO;
import wns.dto.StatusToolDTO;
import wns.entity.Status;
import wns.services.ClientsService;
import wns.services.PageableFilterService;
import wns.services.StatusService;
import wns.services.ToolsService;
import wns.utils.ResponseHandler;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("repair")
@AllArgsConstructor
public class RepairController {

    private final ClientsService clientsService;
    private final ToolsService toolsService;
    private final StatusService statusService;
    private final PageableFilterService pageableFilterService;


    @GetMapping
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model) {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.REPAIR));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("list_statuses", paginated_list);
        return "repair";
    }

    @GetMapping("/create")
    public String showCreatePage(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                 @RequestParam(value = "size", required = false) Optional<Integer> size,
                                 Model model) {
        Page<Object> paginated = pageableFilterService.getListData(new PageDataDTO(page, size,Filter.WAITING));
        pageableFilterService.addPageNumbers(paginated, model);
        model.addAttribute("clients", clientsService.getAll());
        model.addAttribute("list_tools", paginated);
        return "create_repair";
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Object> createRepair(@RequestBody StatusToolDTO statusToolDTO) {
        statusToolDTO.setStatusTools(StatusTools.REPAIR);
        List<Status> statusList = toolsService.changeStatus(statusToolDTO);
        statusList.forEach(statusService::save);
        return ResponseHandler.generateResponse(Messages.OK,"/repair");
    }
}
