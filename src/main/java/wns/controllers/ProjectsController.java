package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Filter;
import wns.constants.Messages;
import wns.constants.PaginationConst;
import wns.constants.StatusTools;
import wns.dto.IdsDTO;
import wns.dto.Identifiers;
import wns.dto.ProjectDTO;
import wns.entity.Project;
import wns.services.ClientsService;
import wns.services.PageableFilterService;
import wns.services.ProjectService;
import wns.services.ToolsService;
import wns.utils.ResponseHandler;

import java.util.*;

@Controller
@RequestMapping("")
@AllArgsConstructor
public class ProjectsController {

    private final ProjectService projectService;
    private final ClientsService clientsService;
    private final ToolsService toolsService;
    private final PageableFilterService pageableFilterService;

    @GetMapping
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       @RequestParam(value = "filter", required = false) Filter filter,
                       Model model) {

        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size, filter, PaginationConst.PROJECT,0);
        pageableFilterService.addPageNumbersToModel(paginated_list, model);
        model.addAttribute("list_projects", paginated_list);
        return "projects";
    }

    @PostMapping("/projects/delete")
    @ResponseBody
    public ResponseEntity<Object> deleteProject(@RequestBody IdsDTO ids) {
        projectService.deleteProjectsByListsId(ids.getIds());
        return ResponseHandler.generateResponse(Messages.DELETE,"/projects");
    }

    @GetMapping("/projects/create")
    public String showCreatingPage(Model model) {
        List<Identifiers> list = new ArrayList<>();
        model.addAttribute("clients", clientsService.getAll());
        model.addAttribute("list_tools", toolsService.getListToolsByStatus(StatusTools.INSTOCK));
        model.addAttribute("projectDTO", new ProjectDTO());
        model.addAttribute("ids",list);
        return "project_create";
    }

    @PostMapping("/projects/create")
    public String createProject(@ModelAttribute ProjectDTO projectDTO) {
        Project project = projectService.createProject(projectDTO);
        return "redirect:/estimate/create/"+project.getId();
    }

    @GetMapping("/projects/edit/{id}")
    public String editProject(@PathVariable("id") long id, Model model) {
        model.addAttribute("clients", clientsService.getAll());
        model.addAttribute("list_tools", toolsService.getListToolsByStatus(StatusTools.INSTOCK));
        model.addAttribute("project", projectService.getById(id));
        return "project_edit";
    }

    @PostMapping("/projects/add-tool/{id}")
    @ResponseBody
    public ResponseEntity<Object> addToolsToProject(@PathVariable("id") long id, @RequestBody IdsDTO ids) {
        Messages messages = projectService.addToolsToProject(id, ids.getNew_ids());
        return ResponseHandler.generateResponse(messages, "/projects/edit/" + id+"/");
    }

    @PostMapping("/projects/change-tools/{id}")
    @ResponseBody
    public ResponseEntity<Object> changeTools(@PathVariable("id") long project_id, @RequestBody IdsDTO ids) {
        Messages messages = projectService.changeToolsInProject(project_id, ids);
        return ResponseHandler.generateResponse(messages,"/projects/edit/"+project_id+"/");
    }

    @PostMapping("/projects/remove-tool/{id}")
    @ResponseBody
    public ResponseEntity<Object> deleteTools(@PathVariable("id") long id, @RequestBody IdsDTO ids) {
        Messages messages = projectService.clearToolsFromProject(ids.getIds());
        return ResponseHandler.generateResponse(messages,"/projects/edit/"+id);
    }

}
