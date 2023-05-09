package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wns.constants.Filter;
import wns.dto.CalendarFilterDTO;
import wns.services.ProjectService;
import wns.services.ToolsService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("calendar")
@AllArgsConstructor
public class CalendarController {

    private final ToolsService toolsService;
    private final ProjectService projectService;


    @GetMapping
    public String showCalendar() {
        return "calendar";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> showByDate(@RequestBody CalendarFilterDTO calendarFilterDTO) {
        Filter filter = calendarFilterDTO.getFilter();
        LocalDate startDate = calendarFilterDTO.getDateStart();
        final List<Object> list = new ArrayList<>();
        if (filter.equals(Filter.PROJECTS_BY_WEEK) || filter.equals(Filter.PROJECTS_BY_MONTH)) {
            list.addAll(projectService.findAllByDates(startDate));
        } else if (filter.equals(Filter.TOOLS_BY_WEEK) || filter.equals(Filter.TOOLS_BY_MONTH)) {
            list.addAll(toolsService.findAllByDates(startDate));
        }
        return ResponseEntity.ok(list);
    }

}
