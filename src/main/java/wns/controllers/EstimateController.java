package wns.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Messages;
import wns.constants.StatusProject;
import wns.dto.EstimateDTO;
import wns.entity.Estimate;
import wns.entity.Project;
import wns.services.EstimateNameService;
import wns.services.EstimateService;
import wns.services.ProjectService;
import wns.utils.ExcelUtil;
import wns.utils.ResponseHandler;

import java.nio.file.FileSystems;

@Controller
@RequestMapping("estimate")
public class EstimateController {
    private final EstimateNameService estimateNameService;
    private final EstimateService estimateService;
    private final ProjectService projectService;
    private final ExcelUtil excelUtil;

    @Value("${fileurl}")
    private String fileUrl;

    public EstimateController(EstimateNameService estimateNameService, EstimateService estimateService, ProjectService projectService, ExcelUtil excelUtil) {
        this.estimateNameService = estimateNameService;
        this.estimateService = estimateService;
        this.projectService = projectService;
        this.excelUtil = excelUtil;
    }

    @GetMapping("/create/{id}")
    public String showEstimateByProject(@PathVariable("id") long id, Model model)
    {
        Project project = projectService.getById(id);
        Estimate estimate = project.getEstimate();
        model.addAttribute("estimate", estimate);
        model.addAttribute("map_tools", estimateService.getToolsEstimate(estimate));
        return "create_estimate";
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
        return ResponseHandler.generateResponse(Messages.RETURN_FILE_URL,FileSystems.getDefault().getSeparator()+ fileUrl + file_path);
    }



    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id)
    {
        estimateNameService.delete(id);
        return "redirect:/";
    }
}
