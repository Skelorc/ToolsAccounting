package wns.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Filter;
import wns.constants.Messages;
import wns.dto.IdsDTO;
import wns.dto.PageDataDTO;
import wns.dto.ProjectDTO;
import wns.dto.WorkingShiftDTO;
import wns.entity.*;
import wns.services.*;
import wns.utils.ResponseHandler;
import wns.utils.excel.ExcelArray;
import wns.utils.excel.ExcelShipment;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static wns.constants.Messages.PROJECT_UPDATE;

@RequiredArgsConstructor
@RequestMapping("")
@Controller
public class ProjectsController {

    private final ProjectService projectService;
    private final ClientsService clientsService;
    private final ToolsService toolsService;
    private final CategoryService categoryService;
    private final ToolsEstimateService toolsEstimateService;
    private final WorkingShiftService workingShiftService;
    private final EstimateService estimateService;
    private final PageableFilterService pageableFilterService;
    private final ExcelShipment excelShipment;
    private final ExcelArray excelArray;

    @Value("${fileurl}")
    private String fileUrl;

    @GetMapping
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       @RequestParam(value = "filter", required = false, defaultValue = "ALL_PROJECTS") String filter,
                       Model model) {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.valueOf(filter)));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("list_projects", paginated_list);
        return "projects";
    }

    @GetMapping("/projects/by-date")
    public String showProjectsByDate(@RequestParam("date")LocalDate localDate, Model model)
    {
        Set<ProjectDTO> projectsByDateShift = projectService.findProjectsByDateShift(localDate).stream().map(ProjectDTO::new).collect(Collectors.toSet());
        model.addAttribute("list_projects",projectsByDateShift);
        return "projects";
    }

    @GetMapping("/projects/create")
    public String showCreatingPage(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                   @RequestParam(value = "size", required = false) Optional<Integer> size,
                                   Model model) {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.INSTOCK));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("clients", clientsService.getAll());
        model.addAttribute("list_tools", paginated_list);
        model.addAttribute("list_categories", categoryService.getAll());
        model.addAttribute("projectDTO", new ProjectDTO());
        return "create_project";
    }

    @PostMapping("/projects/create")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> createProject(@RequestBody ProjectDTO projectDTO) {
        List<WorkingShiftDTO> workingShifts = projectDTO.getWorkingShifts();
        projectDTO.setStart(projectDTO.getStart());
        projectDTO.setEnd(projectDTO.getEnd());
        Client client = clientsService.getById(projectDTO.getClient_id());
        Estimate estimate = new Estimate();
        estimate.setStart(projectDTO.getStart().toLocalDate());
        estimate.setEnd(projectDTO.getEnd().toLocalDate());
        estimate.setCount_shifts(workingShifts.size());
        Project project = projectService.createProject(projectDTO, client, estimate);
        for (WorkingShiftDTO shiftDTO : workingShifts) {
            WorkingShift workingShift = shiftDTO.creteWorkingShiftFromDTO();
            workingShift.setDateShift(shiftDTO.getDateShift());
            workingShift.setProject(project);
            project.getWorkingShifts().add(workingShift);
            workingShiftService.save(workingShift);
        }
        for (Long id : projectDTO.getItems()) {
            Tools tool = toolsService.findById(id);
            toolsService.addToolToProject(tool, project);
            toolsEstimateService.addToolEstimateToEstimate(project, tool);
        }
        return ResponseHandler.generateResponse(Messages.REDIRECT, "/estimate/create/" + project.getId());
    }


    @GetMapping("/projects/edit/{id}")
    public String editProject(@RequestParam(value = "page", required = false) Optional<Integer> page,
                              @RequestParam(value = "size", required = false) Optional<Integer> size,
                              @PathVariable("id") long id, Model model) {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.GET_TOOLS_BY_PROJECT, id));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("clients", clientsService.getAll());
        model.addAttribute("list_tools", paginated_list);
        model.addAttribute("list_categories", categoryService.getAll());
        model.addAttribute("project", projectService.getById(id));
        return "edit_project";
    }

    @PostMapping("/projects/edit")
    @ResponseBody
    public ResponseEntity<Object> updateProject(@RequestBody ProjectDTO projectDTO) {
        Project project = projectService.getById(projectDTO.getId());
        project = projectDTO.updateProjectFromDTO(project);
        Estimate estimate = project.getEstimate();
        estimate.setCount_shifts(projectDTO.getWorkingShifts().size());
        if (project.getClient().getId() != projectDTO.getClient_id()) {
            Client newClient = clientsService.getById(projectDTO.getClient_id());
            project.setClient(newClient);
            project.setPhoneNumber(newClient.getPhoneNumber());
        }
        List<WorkingShift> listShifts = projectDTO.getWorkingShifts().stream().map(WorkingShiftDTO::creteWorkingShiftFromDTO).collect(Collectors.toList());
        project.getWorkingShifts().clear();
        for (WorkingShift shift : listShifts) {
            shift.setProject(project);
            project.getWorkingShifts().add(shift);
            workingShiftService.save(shift);
        }
        estimateService.save(estimate);
        projectService.save(project);
        return ResponseHandler.generateResponse(Messages.REDIRECT, "/");
    }

    @PostMapping("/projects/close")
    @ResponseBody
    public ResponseEntity<Object> closeProject(@RequestBody ProjectDTO dto) {
        projectService.closeProject(dto);
        return ResponseHandler.generateResponse(Messages.REDIRECT, "/");
    }

    @PostMapping("/projects/delete")
    @ResponseBody
    public ResponseEntity<Object> deleteProject(@RequestBody IdsDTO idsDTO) {
        for (Long id : idsDTO.getIds()) {
            Project project = projectService.getById(id);
            project.getTools().forEach(toolsService::deleteToolFromProject);
            project.getWorkingShifts().forEach(workingShiftService::delete);
            projectService.delete(project.getId());
        }
        return ResponseHandler.generateResponse(Messages.DELETE, "/");
    }

    @PostMapping("/projects/add-tool/{id}")
    @ResponseBody
    public ResponseEntity<Object> addToolsToProject(@PathVariable("id") long id, @RequestBody IdsDTO ids) {
        Project project = projectService.getById(id);
        for (Long id_tool : ids.getNew_ids()) {
            Tools tool = toolsService.findById(id_tool);
            toolsService.addToolToProject(tool, project);
            toolsEstimateService.addToolEstimateToEstimate(project, tool);
        }
        return ResponseHandler.generateResponse(PROJECT_UPDATE, "/projects/edit/" + id + "/");
    }

    @PostMapping("/projects/change-tools/{id}")
    @ResponseBody
    public ResponseEntity<Object> changeTools(@PathVariable("id") long project_id, @RequestBody IdsDTO ids) {
        Project project = projectService.getById(project_id);
        for (Tools old_tool : project.getTools()) {
            List<Long> old_ids = ids.getOld_ids();
            for (Long old_id : old_ids) {
                if (old_tool.getId() == old_id) {
                    toolsEstimateService.deleteToolEstimateFromEstimate(old_tool);
                    toolsService.deleteToolFromProject(old_tool);
                }
            }
        }
        for (Long new_id : ids.getNew_ids()) {
            Tools new_tool = toolsService.findById(new_id);
            toolsService.addToolToProject(new_tool, project);
            toolsEstimateService.addToolEstimateToEstimate(project, new_tool);
        }
        return ResponseHandler.generateResponse(Messages.REDIRECT, "/projects/edit/" + project_id + "/");
    }

    @PostMapping("/projects/remove-tool/{id}")
    @ResponseBody
    public ResponseEntity<Object> deleteTools(@PathVariable("id") long id, @RequestBody IdsDTO ids) {
        for (Long id_tool : ids.getIds()) {
            Tools tool = toolsService.findById(id_tool);
            toolsEstimateService.deleteToolEstimateFromEstimate(tool);
            toolsService.deleteToolFromProject(tool);
        }
        return ResponseHandler.generateResponse(Messages.REDIRECT, "/projects/edit/" + id);
    }

    @PostMapping("/projects/download-shipment/{id}")
    public String downloadShipment(@PathVariable("id") long id) throws IOException {
        Estimate estimate = projectService.getById(id).getEstimate();
        excelShipment.createShipment(estimate);
        File file = excelShipment.createFileFromWorkBook("Otgruzka - "+estimate.getProject().getId()+"-"+estimate.getId()+ ", data -" + estimate.getProject().getStart().toLocalDate().toString());
        return "redirect:/"+fileUrl+file.getName();
    }

    @PostMapping("/projects/download-array/{id}")
    public String downloadArray(@PathVariable("id") long id) throws IOException {
        Estimate estimate = projectService.getById(id).getEstimate();
        excelArray.createArray(estimate);
        File file = excelArray.createFileFromWorkBook("Spisok - "+estimate.getProject().getId()+"-"+estimate.getId()+ ", data -" + estimate.getProject().getStart().toLocalDate().toString());
        return "redirect:/"+fileUrl+file.getName();
    }
}
