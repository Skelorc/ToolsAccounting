package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wns.constants.Messages;
import wns.constants.StatusTools;
import wns.dto.IdentifiersStatus;
import wns.dto.ToolsDTO;
import wns.entity.Tools;
import wns.services.EstimateNameService;
import wns.services.PageableService;
import wns.services.ToolsService;
import wns.utils.ResponseHandler;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("tools")
@AllArgsConstructor
public class ToolsController {
    private final ToolsService toolsService;
    private final EstimateNameService estimateNameService;
    private final PageableService pageableService;

    @GetMapping
    public String show(@RequestParam(value = "filter", required = false) String filter,
                             @RequestParam(value = "page", required = false) Optional<Integer> page,
                             @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model)
    {
        Page<Tools> paginated_list = toolsService.findPaginated(page,size,filter);
        pageableService.getPageNumbers(paginated_list,model);
        model.addAttribute("list_tools", paginated_list);
        return "tools";
    }

    @GetMapping
    @RequestMapping("/create")
    public String create(Model model) {
        model.addAttribute("tool", new ToolsDTO());
        model.addAttribute("list_names_estimate", estimateNameService.getAll());
        return "tool_create";
    }

    @PostMapping("/create")
    public String createTools(@ModelAttribute ToolsDTO tool,
                              @RequestParam("name_estimate_id") long name_estimate_id,
                              @RequestParam("status_tool") StatusTools status_tool) {
        toolsService.createTools(tool, name_estimate_id, status_tool);
        return "redirect:/tools/create";
    }

    @GetMapping("/edit/{id}")
    public String showToolForUpdate(@PathVariable("id") long id, Model model)
    {
        ToolsDTO tool = toolsService.findById(id);
        model.addAttribute("tool",tool);
        model.addAttribute("list_names_estimate", estimateNameService.getAll());
        return "tool_create";
    }
    @PostMapping("/change-status")
    public ResponseEntity<Object> changeStatusTool(@RequestBody List<IdentifiersStatus> statuses)
    {
        toolsService.updateStatus(statuses);
        return ResponseHandler.generateResponse(Messages.STATUS_CREATE);
    }
}
