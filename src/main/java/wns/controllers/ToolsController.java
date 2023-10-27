package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Filter;
import wns.constants.Messages;
import wns.constants.StatusTools;
import wns.dto.EstimateNameDTO;
import wns.dto.PageDataDTO;
import wns.dto.StatusToolDTO;
import wns.dto.ToolsDTO;
import wns.entity.*;
import wns.services.*;
import wns.utils.ResponseHandler;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("tools")
@AllArgsConstructor
public class ToolsController {
    private final ToolsService toolsService;
    private final CategoryService categoryService;
    private final StatusService statusService;
    private final EstimateNameService estimateNameService;
    private final PageableFilterService pageableFilterService;
    private final RoleContactService roleContactService;
    private final OwnerService ownerService;
    private final CommentsService commentsService;

    @GetMapping()
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model) {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.ALL_TOOLS));
        pageableFilterService.addPageNumbers(paginated_list, model);
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
        model.addAttribute("roles_contacts", roleContactService.getAll());
        model.addAttribute("list_category", categoryService.getAll());
        model.addAttribute("list_names_estimate", estimateNameService.getAll());
        return "create_tool";
    }

    @PostMapping("/create")
    public String createTools(@ModelAttribute Tools tool,
                              @RequestParam("name_estimate") EstimateName estimateNameDTO,
                              @RequestParam("owner") Owner owner,
                              @RequestParam("category") Category category,
                              @RequestParam("status_tool") StatusTools status_tool) {
        toolsService.createTools(tool, estimateNameDTO, status_tool,category,owner);
        categoryService.save(category);
        return "redirect:/tools/create";
    }

    @GetMapping("/edit/{id}")
    public String showToolForUpdate(@PathVariable("id") long id, Model model) {
        model.addAttribute("tool", new ToolsDTO(toolsService.findById(id)));
        model.addAttribute("list_owners", ownerService.getAll());
        model.addAttribute("list_category", categoryService.getAll());
        model.addAttribute("list_names_estimate", estimateNameService.getAll());
        return "edit_tool";
    }

    @PostMapping("/edit/{id}")
    @Transactional
    public String updateTool(@ModelAttribute ToolsDTO dto)
    {
        Tools tools = dto.FromDTOToTools(toolsService.findById(dto.getId()));
        Comment comment = commentsService.getByTools(tools);
        comment.setText(dto.getComment());
        tools.setComment(comment);
        Status status = tools.getStatus();
        status.setStatusTools(dto.getStatus());
        Owner owner = ownerService.getById(dto.getOwnerId());
        tools.setOwner(owner);
        Category category = categoryService.getById(dto.getCategoryId());
        tools.setCategory(category);
        statusService.save(status);
        toolsService.updateTool(tools);
        return "redirect:/tools";
    }

    @PostMapping("/change-status")
    @ResponseBody
    public ResponseEntity<Object> changeStatusTool(@RequestBody StatusToolDTO statusToolDTO) {
        List<Status> statusList = toolsService.changeStatus(statusToolDTO);
        statusList.forEach(statusService::save);
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
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.STOCK, id));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("list_tools", paginated_list);
        model.addAttribute("project_id", id);
        return "tools";
    }

    @GetMapping("/replace-tool/{id}")
    public String showToolsPage(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                @RequestParam(value = "size", required = false) Optional<Integer> size,
                                @RequestParam(value = "filter", required = false) Filter filter,
                                @RequestParam(value = "ids", required = false) List<Long> ids_tools,
                                @PathVariable(value = "id", required = false) long project_id,
                                Model model) {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size,filter,project_id));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("list_tools", paginated_list);
        model.addAttribute("ids", ids_tools);
        model.addAttribute("project_id", project_id);
        return "tools";
    }
}
