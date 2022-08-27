package wns.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wns.constants.CategoryTools;
import wns.constants.StatusTools;
import wns.constants.TypeTools;
import wns.entity.Tools;
import wns.services.ToolsService;

import java.util.List;

@Controller
@RequestMapping("tools")
@AllArgsConstructor
public class ToolsController {
    private final ToolsService toolsService;

    @GetMapping
    public ModelAndView show()
    {
        List<Tools> allTools = toolsService.getAllTools();
        Tools newTools = new Tools();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allTools",allTools);
        modelAndView.addObject("tools",newTools);
        modelAndView.addObject("typeTools", TypeTools.values());
        modelAndView.addObject("statusTools", StatusTools.values());
        modelAndView.addObject("categoryTools", CategoryTools.values());
        modelAndView.setViewName("tools");
        return modelAndView;
    }

    @GetMapping
    @RequestMapping("/create")
    public ModelAndView create()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("toolsCreate");
        return modelAndView;
    }

    @PostMapping("/create")
    public String createTools(@ModelAttribute Tools tools, Model model)
    {
        String answer = toolsService.createTools(tools);
        model.addAttribute("answer", answer);
        return "redirect:/tools";
    }
}
