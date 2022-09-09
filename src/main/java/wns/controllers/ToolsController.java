package wns.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wns.constants.CategoryTools;
import wns.constants.Messages;
import wns.constants.StatusTools;
import wns.constants.TypeTools;
import wns.dto.ToolsDTO;
import wns.entity.Tools;
import wns.services.ToolsService;
import wns.utils.ResponseHandler;

import java.util.List;

@Controller
@RequestMapping("tools")
@AllArgsConstructor
public class ToolsController {
    private final ToolsService toolsService;

    @GetMapping
    public ModelAndView show()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allTools",toolsService.getAllTools());
        modelAndView.addObject("tools",new ToolsDTO());
        modelAndView.setViewName("tools");
        return modelAndView;
    }

    @GetMapping
    @RequestMapping("/create")
    public ModelAndView create()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tool_create");
        return modelAndView;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createTools(@RequestBody ToolsDTO tools)
    {
        Messages message = toolsService.createTools(tools);
        return ResponseHandler.generateResponse(message);
    }
}
