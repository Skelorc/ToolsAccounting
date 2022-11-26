package wns.controllers;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Filter;
import wns.constants.Messages;
import wns.constants.PaginationConst;
import wns.constants.StatusProject;
import wns.dto.IdsDTO;
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
    private final ObjectMapper mapper;

    @GetMapping
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model) {
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size, Filter.ALL_PROJECTS, PaginationConst.PROJECT,-1);
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
    public String showCreatingPage(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                   @RequestParam(value = "size", required = false) Optional<Integer> size,
                                   Model model) {
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size, Filter.INSTOCK, PaginationConst.TOOLS,-1);
        pageableFilterService.addPageNumbersToModel(paginated_list, model);
        model.addAttribute("clients", clientsService.getAll());
        model.addAttribute("list_tools", paginated_list);
        model.addAttribute("projectDTO", new ProjectDTO());
        return "project_create";
    }

    @PostMapping("/projects/create")
    @ResponseBody
    public ResponseEntity<Object> createProject(@RequestBody ProjectDTO projectDTO) {
        Project project = projectService.createProject(projectDTO);
        return ResponseHandler.generateResponse(Messages.REDIRECT,"/estimate/create/"+project.getId());
    }

    @GetMapping("/projects/edit/{id}")
    public String editProject(@RequestParam(value = "page", required = false) Optional<Integer> page,
                              @RequestParam(value = "size", required = false) Optional<Integer> size,
                              @PathVariable("id") long id, Model model) {
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size, Filter.GET_TOOLS_BY_PROJECT, PaginationConst.PROJECT,id);
        pageableFilterService.addPageNumbersToModel(paginated_list, model);
        model.addAttribute("clients", clientsService.getAll());
        model.addAttribute("list_tools", paginated_list);
        model.addAttribute("project",projectService.getById(id));
        return "edit_project";
    }

    @PostMapping("/projects/edit")
    @ResponseBody
    public ResponseEntity<Object> updateProject(@RequestBody ProjectDTO projectDTO)
    {
        System.out.println(projectDTO);
        projectService.updateProject(projectDTO);
        return ResponseHandler.generateResponse(Messages.REDIRECT,"/");
    }

    @PostMapping("/projects/close")
    @ResponseBody
    public ResponseEntity<Object> closeProject(@RequestBody ProjectDTO dto)
    {
        projectService.closeProject(dto);
        return ResponseHandler.generateResponse(Messages.REDIRECT,"/");
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
