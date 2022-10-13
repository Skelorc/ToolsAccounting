package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wns.constants.Messages;
import wns.dto.EstimateNameDTO;
import wns.dto.ProjectDTO;
import wns.entity.Project;
import wns.services.EstimateNameService;
import wns.services.PageableService;
import wns.services.ProjectService;

import java.time.Period;
import java.util.Optional;

@Controller
@RequestMapping("estimate")
@AllArgsConstructor
public class EstimateController {
    private final EstimateNameService estimateService;
    private final ProjectService projectService;
    private final PageableService pageableService;

    @GetMapping
    public String show(@RequestParam(value = "filter", required = false) String filter,
                       @RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model)
    {
        Page<EstimateNameDTO> paginated_list = estimateService.findPaginated(page, size, filter);
        pageableService.getPageNumbers(paginated_list,model);
        model.addAttribute("estimateDTO",new EstimateNameDTO());
        model.addAttribute("list_estimate",paginated_list);
        return "estimate";
    }

    @PostMapping
    public String create(@ModelAttribute("estimtateDTO") EstimateNameDTO estimateNameDTO, Model model)
    {
        Messages message = estimateService.save(estimateNameDTO);
        model.addAttribute("message",message);
        return "redirect:/estimate";
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("project") Project project, Model model)
    {
        if(project.getStart()!=null) {
            model.addAttribute("project", project);
            model.addAttribute("count_shifts", Period.between(project.getStart().toLocalDate(), project.getEnd().toLocalDate()).getDays());
            return "estimate_create";
        }
        return "redirect:/estimate";
    }

    @GetMapping("/create/{id}")
    public String showEstimateByProject(@PathVariable("id") long id, Model model)
    {
        Project project = projectService.getById(id);
        int days = Period.between(project.getStart().toLocalDate(), project.getEnd().toLocalDate()).getDays();
        if(days<=0)
            days=1;
        model.addAttribute("project", project);
        model.addAttribute("count_shifts", days);
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
