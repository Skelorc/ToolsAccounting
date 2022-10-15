package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wns.constants.Messages;
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
@RequestMapping("write-off")
@AllArgsConstructor
public class OffsController {
    private final ClientsService clientsService;
    private final StatusService statusService;
    private final ToolsService toolsService;
    private final PageableService pageableService;

    @GetMapping
    public String show(Model model) {
        model.addAttribute("list_statuses", statusService.getListByStatuses(StatusTools.WRITENOFF));
        return "write_off";
    }

    @GetMapping("/create")
    public String create(@RequestParam(value = "page", required = false) Optional<Integer> page,
                         @RequestParam(value = "size", required = false) Optional<Integer> size,
                         @ModelAttribute("message") String message,
                         Model model)
    {
        model.addAttribute("clients", clientsService.getAll());
        List<Tools> list = toolsService.getListToolsByStatus(StatusTools.WAITING);
        Page<Tools> paginated = pageableService.findPaginated(page, size, list);
        pageableService.addPageNumbersToModel(paginated, model);
        model.addAttribute("list_tools", paginated);
        model.addAttribute("status", new TypeStatusDTO());
        model.addAttribute("message", message);
        return "write_off_create";
    }

    @PostMapping("/create")
    public String createWriteOff(@ModelAttribute TypeStatusDTO typeStatusDTO, RedirectAttributes redirectAttributes) {
        typeStatusDTO.setStatusTools(StatusTools.WRITENOFF);
        Messages messages = toolsService.changeStatus(typeStatusDTO);
        redirectAttributes.addFlashAttribute("message", messages.getValue());
        return "redirect:/write-off/create";
    }
}
