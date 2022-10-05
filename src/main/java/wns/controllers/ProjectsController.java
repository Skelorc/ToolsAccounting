package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wns.constants.StatusTools;
import wns.dto.ProjectDTO;
import wns.services.ClientsService;
import wns.services.ProjectService;
import wns.services.ToolsService;

@Controller
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectsController {

    private final ProjectService projectService;
    private final ClientsService clientsService;
    private final ToolsService toolsService;

    @GetMapping
    public String show()
    {
        return "projects";
    }

    @GetMapping("/create")
    public String showCreatingPage(Model model)
    {
        model.addAttribute("clients", clientsService.getAll());
        model.addAttribute("list_tools", toolsService.getListToolsByStatus(StatusTools.INSTOCK));
        model.addAttribute("projectDTO", new ProjectDTO());
        return "project_create";
    }

    @PostMapping("/create")
    public String createProject(@ModelAttribute ProjectDTO projectDTO, RedirectAttributes redirectAttributes, Model model)
    {
            redirectAttributes.addFlashAttribute("project",projectService.createProject(projectDTO));
            return "redirect:/estimate/create";
    }
}
