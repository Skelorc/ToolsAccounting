package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wns.dto.EstimateNameDTO;
import wns.dto.ProjectDTO;
import wns.entity.Project;
import wns.services.EstimateNameService;

import java.time.Period;

@Controller()
@RequestMapping("estimate")
@AllArgsConstructor
public class EstimateController {
    private final EstimateNameService estimateService;

    @GetMapping
    public String show(Model model)
    {
        model.addAttribute("estimateDTO",new EstimateNameDTO());
        model.addAttribute("all",estimateService.getAll());
        return "estimate";
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("projectDTO") ProjectDTO project, Model model)
    {
        model.addAttribute("projectDTO",project);
        model.addAttribute("count_shifts", Period.between(project.getStart().toLocalDate(), project.getEnd().toLocalDate()).getDays());
        System.out.println(project);
        return "estimate_create";
    }

    @PostMapping("/create")
    public Model createEstimateName(@ModelAttribute EstimateNameDTO dto, Model model)
    {
        model.addAttribute("message",estimateService.save(dto).getValue());
        model.addAttribute("estimateDTO",new EstimateNameDTO());
        model.addAttribute("all",estimateService.getAll());
        return model;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id)
    {
        estimateService.delete(id);
        return "redirect:/estimate";
    }
}
