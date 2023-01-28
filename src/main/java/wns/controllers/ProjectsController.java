package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static wns.constants.Messages.PROJECT_UPDATE;

@Controller
@RequestMapping("")
@AllArgsConstructor
public class ProjectsController {

    private final ProjectService projectService;
    private final ClientsService clientsService;
    private final ToolsService toolsService;
    private final ToolsEstimateService toolsEstimateService;
    private final WorkingShiftService workingShiftService;
    private final PageableFilterService pageableFilterService;

    @GetMapping
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model) {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.ALL_PROJECTS));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("list_projects", paginated_list);
        return "projects";
    }

    @PostMapping("/projects/delete")
    @ResponseBody
    public ResponseEntity<Object> deleteProject(@RequestBody IdsDTO idsDTO) {
        for (Long id : idsDTO.getIds()) {
            Project project = projectService.getById(id);
            Set<Tools> tools = project.getTools();
            projectService.delete(project.getId());
            for (Tools tool : tools) {
                toolsEstimateService.deleteToolEstimateFromEstimate(tool);
                toolsService.deleteToolFromProject(tool);
            }
            for (WorkingShift workingShift : project.getWorkingShifts()) {
                workingShiftService.delete(workingShift.getId());
            }
        }
        return ResponseHandler.generateResponse(Messages.DELETE, "/projects");
    }

    @GetMapping("/projects/create")
    public String showCreatingPage(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                   @RequestParam(value = "size", required = false) Optional<Integer> size,
                                   Model model) {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.INSTOCK));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("clients", clientsService.getAll());
        model.addAttribute("list_tools", paginated_list);
        model.addAttribute("projectDTO", new ProjectDTO());
        return "create_project";
    }

    @PostMapping("/projects/create")
    @ResponseBody
    public ResponseEntity<Object> createProject(@RequestBody ProjectDTO projectDTO) {
        List<WorkingShiftDTO> workingShifts = projectDTO.getWorkingShifts();
        projectDTO.setStart(workingShifts.get(0).getDateShift());
        projectDTO.setEnd(workingShifts.get(workingShifts.size()-1).getDateShift());
        Client client = clientsService.getById(projectDTO.getClient_id());
        Estimate estimate = new Estimate();
        estimate.setStart(projectDTO.getStart());
        estimate.setEnd(projectDTO.getEnd());
        estimate.setCount_shifts(workingShifts.size());
        Project project = projectService.createProject(projectDTO, client, estimate);
        for (Long id : projectDTO.getItems()) {
            Tools tool = toolsService.findById(id);
            toolsService.addToolToProject(tool, project);
            toolsEstimateService.addToolEstimateToEstimate(project, tool);
        }
        for (WorkingShiftDTO shiftDTO : workingShifts) {
            WorkingShift workingShift = shiftDTO.creteWorkingShiftFromDTO(shiftDTO);
            workingShift.setDateShift(shiftDTO.getDateShift());
            workingShift.setProject(project);
            workingShiftService.save(workingShift);
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
        model.addAttribute("project", projectService.getById(id));
        return "edit_project";
    }

    @PostMapping("/projects/edit")
    @ResponseBody
    public ResponseEntity<Object> updateProject(@RequestBody ProjectDTO projectDTO) {
        List<WorkingShift> workingShiftList = new ArrayList<>();
        Project project = projectDTO.createProjectFromDTO();
        project.setStart(workingShiftList.get(0).getDateShift());
        project.setEnd(workingShiftList.get(workingShiftList.size()-1).getDateShift());
        Client client = clientsService.getById(projectDTO.getClient_id());
        client.getProjects().add(project);
        project.setClient(client);
        project.setPhoneNumber(client.getPhoneNumber());
        for (WorkingShift workingShift : workingShiftList) {
            workingShift.setProject(project);
            workingShiftService.save(workingShift);
        }
        clientsService.updateClient(client);
        projectService.save(project);
        return ResponseHandler.generateResponse(Messages.REDIRECT, "/");
    }

    @PostMapping("/projects/close")
    @ResponseBody
    public ResponseEntity<Object> closeProject(@RequestBody ProjectDTO dto) {
        projectService.closeProject(dto);
        return ResponseHandler.generateResponse(Messages.REDIRECT, "/");
    }

    @PostMapping("/projects/add-tool/{id}")
    @ResponseBody
    public ResponseEntity<Object> addToolsToProject(@PathVariable("id") long id, @RequestBody IdsDTO ids) {
        Project project = projectService.getById(id);
        for (Long id_tool : ids.getIds()) {
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
            Tools tool = toolsService.getById(id_tool);
            toolsEstimateService.deleteToolEstimateFromEstimate(tool);
            toolsService.deleteToolFromProject(tool);
        }
        return ResponseHandler.generateResponse(Messages.REDIRECT, "/projects/edit/" + id);
    }

}
