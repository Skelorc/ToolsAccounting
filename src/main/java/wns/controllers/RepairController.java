package wns.controllers;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.StatusTools;
import wns.dto.TypeStatusDTO;
import wns.entity.Status;
import wns.entity.Tools;
import wns.services.ClientsService;
import wns.services.PageableService;
import wns.services.StatusService;
import wns.services.ToolsService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/repair")
@AllArgsConstructor
public class RepairController {

    private final ClientsService clientsService;
    private final ToolsService toolsService;
    private final StatusService statusService;
    private final PageableService pageableService;


    @GetMapping
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size, Model model) {
        Page<Status> paginated_list = statusService.findPaginated(page, size, statusService.getListByStatuses(StatusTools.REPAIR));
        pageableService.getPageNumbers(paginated_list, model);
        model.addAttribute("list_statuses", paginated_list);
        return "repair";
    }

    @GetMapping("/create")
    public String showCreatePage(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                 @RequestParam(value = "size", required = false) Optional<Integer> size,
                                 @ModelAttribute("message") String message, Model model) {
        model.addAttribute("clients", clientsService.getAll());
        List<Tools> list = toolsService.getListToolsByStatus(StatusTools.WAITING);
        Page<Tools> paginated = pageableService.findPaginated(page, size, list);
        pageableService.getPageNumbers(paginated, model);
        model.addAttribute("list_tools", paginated);
        model.addAttribute("status", new TypeStatusDTO());
        model.addAttribute("message", message);
        return "repair_create";
    }

    @PostMapping("/create")
    public String createRepair(@ModelAttribute TypeStatusDTO typeStatusDTO) {
        typeStatusDTO.setStatusTools(StatusTools.REPAIR);
        toolsService.changeStatus(typeStatusDTO);
        return "redirect:/repair/create";
    }
}
