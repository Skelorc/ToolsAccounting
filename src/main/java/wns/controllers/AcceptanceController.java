package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wns.entity.Client;
import wns.entity.Tools;
import wns.services.ClientsService;
import wns.services.ProjectService;

@Controller
@AllArgsConstructor
@RequestMapping("acceptance")
public class AcceptanceController {

    private final ProjectService projectService;
    private final ClientsService clientsService;

    @GetMapping
    public String show(Model model)
    {
        model.addAttribute("list_projects",projectService.getAll());
        return "acceptance";
    }

    @GetMapping("/create/{id}")
    public String showClose(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("project",projectService.getById(id));
        model.addAttribute("tool",new Tools());
        model.addAttribute("clients",clientsService.getAll());
        return "acceptance_close";
    }
}
