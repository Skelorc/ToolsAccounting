package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Messages;
import wns.dto.EstimateNameDTO;
import wns.entity.Estimate;
import wns.entity.Project;
import wns.entity.ToolsEstimate;
import wns.services.EstimateNameService;
import wns.services.EstimateService;
import wns.services.PageableService;
import wns.services.ProjectService;
import wns.utils.ExcelUtil;
import wns.utils.ResponseHandler;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("estimate")
@AllArgsConstructor
public class EstimateController {
    private final EstimateNameService estimateNameService;
    private final EstimateService estimateService;
    private final ProjectService projectService;
    private final PageableService pageableService;
    private final ExcelUtil excelUtil;

    @GetMapping
    public String show(@RequestParam(value = "filter", required = false) String filter,
                       @RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model)
    {
        Page<EstimateNameDTO> paginated_list = estimateNameService.findPaginated(page, size, filter);
        pageableService.addPageNumbersToModel(paginated_list,model);
        model.addAttribute("estimateDTO",new EstimateNameDTO());
        model.addAttribute("list_estimate",paginated_list);
        return "estimate";
    }

    @PostMapping
    public String create(@ModelAttribute("estimtateDTO") EstimateNameDTO estimateNameDTO, Model model)
    {
        Messages message = estimateNameService.save(estimateNameDTO);
        model.addAttribute("message",message);
        return "redirect:/estimate";
    }


    @GetMapping("/create/{id}")
    public String showEstimateByProject(@PathVariable("id") long id, Model model)
    {
        Project project = projectService.getById(id);
        Estimate estimate = project.getEstimate();
        model.addAttribute("estimate", estimate);
        model.addAttribute("map_tools", estimateService.getToolsEstimate(estimate));
        return "estimate_create";
    }

    @PostMapping("/create")
    public Model createEstimateName(@ModelAttribute EstimateNameDTO dto, Model model)
    {
        model.addAttribute("message", estimateNameService.save(dto).getValue());
        model.addAttribute("estimateDTO",new EstimateNameDTO());
        model.addAttribute("all", estimateNameService.getAll());
        return model;
    }

    @PostMapping("/create/{id}")
    @ResponseBody
    public ResponseEntity<Object> createEstimateName(@PathVariable("id") long id,
                                                     @RequestBody List<ToolsEstimate> estimateList)
    {
        estimateList.forEach(System.out::println);
        Messages messages = estimateService.updateEstimate(id, estimateList);
        return ResponseHandler.generateResponse(messages);
    }

    @PostMapping("/download-estimate/{id}")
    @ResponseBody
    public ResponseEntity<Object> downloadEstimate(@PathVariable("id") long id)
    {
        Project project = projectService.getById(id);
        Estimate estimate = project.getEstimate();
        excelUtil.createDocumentAndAddHeaders(estimate,estimate.getProject().getName());
        return ResponseHandler.generateResponse(Messages.OK);
    }



    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id)
    {
        estimateNameService.delete(id);
        return "redirect:/estimate";
    }
}
