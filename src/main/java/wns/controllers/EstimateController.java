package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Messages;
import wns.constants.StatusProject;
import wns.dto.EstimateDTO;
import wns.dto.EstimateNameDTO;
import wns.entity.Estimate;
import wns.entity.Project;
import wns.services.EstimateNameService;
import wns.services.EstimateService;
import wns.services.ProjectService;
import wns.utils.ExcelUtil;
import wns.utils.ResponseHandler;

@Controller
@RequestMapping("estimate")
@AllArgsConstructor
public class EstimateController {
    private final EstimateNameService estimateNameService;
    private final EstimateService estimateService;
    private final ProjectService projectService;
    private final ExcelUtil excelUtil;

    @GetMapping("/create/{id}")
    public String showEstimateByProject(@PathVariable("id") long id, Model model)
    {
        Project project = projectService.getById(id);
        Estimate estimate = project.getEstimate();
        model.addAttribute("estimate", estimate);
        model.addAttribute("map_tools", estimateService.getToolsEstimate(estimate));
        return "estimate_create";
    }

    @PostMapping("/create/{id}")
    @ResponseBody
    public ResponseEntity<Object> createEstimateName(@PathVariable("id") long id,
                                                     @RequestBody EstimateDTO dto)
    {
        Estimate estimate = dto.createEstimateFromDTO(projectService.getById(id));
        Project project = estimate.getProject();
        project.setStatus(StatusProject.IN_WORK);
        estimateService.save(estimate);
        projectService.save(project);
        return ResponseHandler.generateResponse(Messages.REDIRECT,"/");
    }

    @PostMapping("/download-estimate/{id}")
    @ResponseBody
    public ResponseEntity<Object> downloadEstimate(@PathVariable("id") long id,
                                                   @RequestBody EstimateDTO dto)
    {
        Estimate estimate = dto.createEstimateFromDTO(projectService.getById(id));
        String file_path = excelUtil.createDocument(estimate);
        return ResponseHandler.generateResponse(Messages.RETURN_FILE_URL,"/"+file_path);
    }



    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id)
    {
        estimateNameService.delete(id);
        return "redirect:/";
    }
}
