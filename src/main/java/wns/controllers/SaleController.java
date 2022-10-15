package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.StatusTools;
import wns.dto.TypeStatusDTO;
import wns.entity.Tools;
import wns.services.ClientsService;
import wns.services.PageableService;
import wns.services.StatusService;
import wns.services.ToolsService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("sale")
@AllArgsConstructor
public class SaleController {

    private final StatusService statusService;
    private final ClientsService clientsService;
    private final ToolsService toolsService;
    private final PageableService pageableService;

    @GetMapping
    public String show(Model model) {
        model.addAttribute("list_statuses", statusService.getListByStatuses(StatusTools.SALES));
        return "sale";
    }

    @GetMapping("/create")
    public String create(@RequestParam(value = "page", required = false) Optional<Integer> page,
                         @RequestParam(value = "size", required = false) Optional<Integer> size,
                         @ModelAttribute("message") String message,
                         Model model) {
        model.addAttribute("clients", clientsService.getAll());
        List<Tools> list = toolsService.getListToolsByStatus(StatusTools.INSTOCK);
        Page<Tools> paginated = pageableService.findPaginated(page, size, list);
        pageableService.addPageNumbersToModel(paginated, model);
        model.addAttribute("list_tools", paginated);
        model.addAttribute("status", new TypeStatusDTO());
        model.addAttribute("message", message);
        return "sale_create";
    }

    @PostMapping("/create")
    public String createSale(@ModelAttribute TypeStatusDTO typeStatusDTO) {
        typeStatusDTO.setStatusTools(StatusTools.SALES);
        toolsService.changeStatus(typeStatusDTO);
        return "redirect:/sale/create";
    }

}
