package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Messages;
import wns.constants.StatusTools;
import wns.dto.IdentifiersStatus;
import wns.dto.ToolsDTO;
import wns.entity.Project;
import wns.entity.Tools;
import wns.services.EstimateNameService;
import wns.services.PageableService;
import wns.services.ToolsService;
import wns.utils.ResponseHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("tools")
@AllArgsConstructor
public class ToolsController {
    private final ToolsService toolsService;
    private final EstimateNameService estimateNameService;
    private final PageableService pageableService;

    @GetMapping()
    public String show(@RequestParam(value = "filter", required = false) String filter,
                       @RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model) {
        Page<ToolsDTO> paginated_list = toolsService.findPaginated(page, size, filter);
        pageableService.getPageNumbers(paginated_list, model);
        model.addAttribute("list_tools", paginated_list);
        return "tools";
    }

    @GetMapping("/pagination")
    @ResponseBody
    public ResponseEntity<Object> pagination(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                             @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Page<ToolsDTO> paginated_list = toolsService.findPaginated(page, size, StatusTools.INSTOCK.toString());
        int totalPages = paginated_list.getTotalPages();
        List<Integer> pageNumbers = null;
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        Map<Integer, Object> data = new HashMap<>();
        data.put(1,paginated_list);
        data.put(2,pageNumbers);
        return ResponseHandler.generateResponse(Messages.OK,"",data);
    }

    @GetMapping("/add-tool-to-project/{id}")
    public String showToolsPage(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                @RequestParam(value = "size", required = false) Optional<Integer> size,
                                @PathVariable(value = "id",required = false) long id,
                                Model model) {
        Page<ToolsDTO> paginated_list = toolsService.findPaginated(page, size, StatusTools.INSTOCK.toString(), id);
        pageableService.getPageNumbers(paginated_list, model);
        model.addAttribute("list_tools", paginated_list);
        model.addAttribute("project_id", id);
        return "tools";
    }

    @GetMapping("/replace-tool/{id}")
    public String showToolsPage(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                @RequestParam(value = "size", required = false) Optional<Integer> size,
                                @PathVariable("id") long project_id,
                                @RequestParam(value = "ids", required = false) List<Long> ids_tools,
                                @RequestParam(value = "status") StatusTools statusTools,
                                Model model) {
        Page<ToolsDTO> paginated_list = toolsService.findPaginated(page, size, statusTools.toString(),project_id);
        pageableService.getPageNumbers(paginated_list, model);
        model.addAttribute("list_tools", paginated_list);
        model.addAttribute("ids", ids_tools);
        model.addAttribute("project_id", project_id);
        return "tools";
    }

    @GetMapping
    @RequestMapping("/create")
    public String create(Model model) {
        model.addAttribute("tool", new Tools());
        model.addAttribute("list_names_estimate", estimateNameService.getAll());
        return "tool_create";
    }

    @PostMapping("/create")
    public String createTools(@ModelAttribute Tools tool,
                              @RequestParam("name_estimate_id") long name_estimate_id,
                              @RequestParam("status_tool") StatusTools status_tool) {
        toolsService.createTools(tool, name_estimate_id, status_tool);
        return "redirect:/tools/create";
    }

    @GetMapping("/edit/{id}")
    public String showToolForUpdate(@PathVariable("id") long id, Model model) {
        model.addAttribute("tool", toolsService.findById(id));
        model.addAttribute("list_names_estimate", estimateNameService.getAll());
        return "tool_edit";
    }

    @PostMapping("/change-status")
    @ResponseBody
    public ResponseEntity<Object> changeStatusTool(@RequestBody List<IdentifiersStatus> statuses) {
        toolsService.updateStatus(statuses);
        return ResponseHandler.generateResponse(Messages.STATUS_CREATE);
    }
}
