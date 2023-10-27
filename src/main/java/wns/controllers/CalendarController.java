package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wns.constants.ClassificationProject;
import wns.constants.EstimateSection;
import wns.constants.Filter;
import wns.constants.Roles;
import wns.dto.*;
import wns.entity.Project;
import wns.entity.Tools;
import wns.services.ClientsService;
import wns.services.ProjectService;
import wns.services.ToolsService;
import wns.services.UserService;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("calendar")
@AllArgsConstructor
public class CalendarController {

    private final ToolsService toolsService;
    private final ProjectService projectService;
    private final UserService userService;
    private final ClientsService clientsService;

    @GetMapping
    public String showCalendar() {
        return "calendar";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> showByFilters(@RequestBody CalendarFilterDTO calendarFilterDTO) {
        Filter filter = calendarFilterDTO.getFilter();
        LocalDate startDate = calendarFilterDTO.getDateStart();
        List<Object> list = new ArrayList<>();
        switch (filter) {
            case PROJECTS_BY_WEEK, PROJECTS_BY_MONTH -> {
                list.addAll(projectService.findAllByFilter(calendarFilterDTO.getFilters()));
            }
            case TOOLS_BY_WEEK, TOOLS_BY_MONTH -> {
                list.addAll(toolsService.findAllByFilter(startDate, calendarFilterDTO));
            }
        }

        return ResponseEntity.ok(list);
    }

    @PostMapping("/filters")
    @ResponseBody
    public ResponseEntity<?> sendFilters(@RequestBody CalendarFilterDTO data) {
        FiltersByCalendarDTO filters = new FiltersByCalendarDTO();
        if (data.getFilter().equals(Filter.ALL_TOOLS)) {
            toolsService.getAll().forEach(x -> filters.getNames().add(x.getName()));
            filters.getTypes().addAll(Arrays.stream(EstimateSection.values()).map(EstimateSection::toString).collect(Collectors.toList()));
        } else {
            projectService.getAll().forEach(x -> filters.getNames().add(x.getName()));
            filters.getTypes().addAll(Arrays.stream(ClassificationProject.values()).map(ClassificationProject::toString).collect(Collectors.toList()));
        }
        clientsService.getAll().forEach(x -> filters.getClients().add(x.getFullName()));
        List<UserDTO> all = userService.getAll();
        for (UserDTO dto : all) {
            if (dto.getRoles().equals(Roles.MANAGER))
                filters.getManagers().add(dto.getUsername());
            else if (dto.getRoles().equals(Roles.WORKER))
                filters.getWorkers().add(dto.getFullName());
        }
        return ResponseEntity.ok(filters);
    }



}
