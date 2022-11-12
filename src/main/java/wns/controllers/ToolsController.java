package wns.controllers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.*;
import wns.dto.EstimateNameDTO;
import wns.dto.StatusToolDTO;
import wns.entity.Category;
import wns.entity.EstimateName;
import wns.entity.Owner;
import wns.entity.Tools;
import wns.repo.CategoryRepo;
import wns.services.*;
import wns.utils.ResponseHandler;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("tools")
@AllArgsConstructor
public class ToolsController {
    private final ToolsService toolsService;
    private final EstimateNameService estimateNameService;
    private final PageableFilterService pageableFilterService;
    private final OwnerService ownerService;
    private final CategoryService categoryService;

    @GetMapping()
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model) {
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size, Filter.WITHOUT_FILTER, PaginationConst.TOOLS,-1);
        pageableFilterService.addPageNumbersToModel(paginated_list, model);
        model.addAttribute("list_tools", paginated_list);
        model.addAttribute("estimateDTO", new EstimateNameDTO());
        model.addAttribute("project_id", -1);
        return "tools";
    }


    @GetMapping
    @RequestMapping("/create")
    public String create(Model model) {
        model.addAttribute("tool", new Tools());
        model.addAttribute("list_owners", ownerService.getAll());
        model.addAttribute("list_category", categoryService.getAll());
        model.addAttribute("list_names_estimate", estimateNameService.getAll());
        return "tool_create";
    }

    @PostMapping("/create")
    public String createTools(@ModelAttribute Tools tool,
                              @RequestParam("name_estimate") EstimateName estimateNameDTO,
                              @RequestParam("owner") Owner owner,
                              @RequestParam("category") Category category,
                              @RequestParam("status_tool") StatusTools status_tool) {
        toolsService.createTools(tool, estimateNameDTO, status_tool,category,owner);
        return "redirect:/tools/create";
    }

    @GetMapping("/edit/{id}")
    public String showToolForUpdate(@PathVariable("id") long id, Model model) {
        model.addAttribute("tool", toolsService.findById(id));
        model.addAttribute("list_owners", ownerService.getAll());
        model.addAttribute("list_category", categoryService.getAll());
        model.addAttribute("list_names_estimate", estimateNameService.getAll());
        return "tool_edit";
    }

    @PostMapping("/edit/{id}")
    public String updateTool(@ModelAttribute Tools tools)
    {
        toolsService.updateTool(tools);
        return "redirect:/tools";
    }

    @PostMapping("/change-status")
    @ResponseBody
    public ResponseEntity<Object> changeStatusTool(@RequestBody StatusToolDTO statusToolDTO) {
        System.out.println(statusToolDTO);
        toolsService.changeStatus(statusToolDTO);
        if(statusToolDTO.getStatusTools().equals(StatusTools.WRITEOFF))
            return ResponseHandler.generateResponse(Messages.REDIRECT,"/write-off");
        if(statusToolDTO.getStatusTools().equals(StatusTools.SALE))
            return ResponseHandler.generateResponse(Messages.REDIRECT,"/sale");
        if(statusToolDTO.getStatusTools().equals(StatusTools.REPAIR))
            return ResponseHandler.generateResponse(Messages.REDIRECT,"/repair");
        return ResponseHandler.generateResponse(Messages.OK);
    }


    @GetMapping("/add-tool-to-project/{id}")
    public String showToolsPage(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                @RequestParam(value = "size", required = false) Optional<Integer> size,
                                @PathVariable(value = "id",required = false) long id,
                                Model model) {
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size, Filter.STOCK,PaginationConst.TOOLS, id);
        pageableFilterService.addPageNumbersToModel(paginated_list, model);
        model.addAttribute("list_tools", paginated_list);
        model.addAttribute("project_id", id);
        return "tools";
    }

    @GetMapping("/replace-tool/{id}")
    public String showToolsPage(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                @RequestParam(value = "size", required = false) Optional<Integer> size,
                                @RequestParam(value = "filter", required = false) Filter filter,
                                @PathVariable("id") long project_id,
                                @RequestParam(value = "ids", required = false) List<Long> ids_tools,
                                Model model) {
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size,filter,PaginationConst.CHANGE,project_id);
        pageableFilterService.addPageNumbersToModel(paginated_list, model);
        model.addAttribute("list_tools", paginated_list);
        model.addAttribute("ids", ids_tools);
        model.addAttribute("project_id", project_id);
        return "tools";
    }

}
