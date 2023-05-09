package wns.controllers;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
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
import wns.services.ClientsService;
import wns.services.EstimateNameService;
import wns.services.EstimateService;
import wns.services.ProjectService;
import wns.utils.excel.ExcelEstimate;
import wns.utils.ResponseHandler;
import wns.utils.pdf.PDFEstimate;

import java.io.*;
import java.nio.file.FileSystems;

@Controller
@RequestMapping("estimate")
@RequiredArgsConstructor
public class EstimateController {
    private final EstimateNameService estimateNameService;
    private final EstimateService estimateService;
    private final ProjectService projectService;
    private final ClientsService clientsService;
    private final ExcelEstimate excelEstimate;
    private final PDFEstimate pdfEstimate;

    @Value("${fileurl}")
    private String fileUrl;

    @GetMapping("/create/{id}")
    public String showEstimateByProject(@PathVariable("id") long id, Model model)
    {
        Project project = projectService.getById(id);
        Estimate estimate = project.getEstimate();
        model.addAttribute("estimate", estimate);
        model.addAttribute("map_tools", estimateService.getToolsEstimate(estimate));
        model.addAttribute("list_clients", clientsService.getAll());
        return "create_estimate";
    }

    @PostMapping("/create/{id}")
    @ResponseBody
    public ResponseEntity<Object> createEstimate(@PathVariable("id") long id,
                                                 @RequestBody EstimateDTO dto)
    {
        Project project = projectService.getById(id);
        Estimate estimate = dto.setDataToEstimateFromDTO(project.getEstimate());
        estimate.setProject(project);
        project.setStatus(StatusProject.IN_WORK);
        estimateService.save(estimate);
        projectService.save(project);
        return ResponseHandler.generateResponse(Messages.REDIRECT,"/");
    }

    @PostMapping("/download-estimate-excel/{id}")
    public ResponseEntity<Object> downloadEstimateExcel(@PathVariable("id") long id, @RequestBody EstimateDTO dto) throws IOException, DocumentException {
        Estimate estimate = estimateService.findById(id);
        dto.setDataToEstimateFromDTO(estimate);
        estimateService.save(estimate);
        excelEstimate.createEstimate(estimate);
        File file = excelEstimate.createFileFromWorkBook("Smeta-" + estimate.getProject().getId() + "-" + estimate.getId() + ",data-" + estimate.getProject().getStart().toLocalDate().toString());
        return ResponseHandler.generateResponse(Messages.RETURN_FILE_URL, FileSystems.getDefault().getSeparator()+ fileUrl+file.getName());
    }

    @PostMapping("/download-estimate-pdf/{id}")
    public ResponseEntity<Object> downloadEstimatePDF(@PathVariable("id") long id, @RequestBody EstimateDTO dto) throws IOException, DocumentException {
        Estimate estimate = estimateService.findById(id);
        dto.setDataToEstimateFromDTO(estimate);
        estimateService.save(estimate);
        File file = pdfEstimate.createDocument(estimate,"Smeta-" + estimate.getProject().getId()+ ",data-" + estimate.getProject().getStart().toLocalDate().toString() + ".pdf");
        return ResponseHandler.generateResponse(Messages.RETURN_FILE_URL, FileSystems.getDefault().getSeparator()+ fileUrl+file.getName());
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id)
    {
        estimateNameService.delete(id);
        return "redirect:/";
    }
}
