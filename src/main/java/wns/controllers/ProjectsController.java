package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wns.services.ProjectService;

@Controller
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectsController {

    private final ProjectService projectService;

    @GetMapping
    public ModelAndView show()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("projects");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreatingPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("projectsCreate");
        return modelAndView;
    }

}
