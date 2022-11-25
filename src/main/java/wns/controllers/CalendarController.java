package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wns.constants.Filter;
import wns.constants.PaginationConst;
import wns.services.PageableFilterService;
import wns.services.ProjectService;
import wns.services.ToolsService;

import java.util.Optional;


@Controller
@RequestMapping("calendar")
@AllArgsConstructor
public class CalendarController {
private final PageableFilterService pageableFilterService;

    @GetMapping
    public String showByDate(@RequestParam(value ="page", required = false) Optional<Integer> page,
                             @RequestParam(value ="size", required = false) Optional<Integer> size,
                             Model model)
    {
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size, Filter.ALL_PROJECTS, PaginationConst.PROJECT, -1);
        pageableFilterService.addPageNumbersToModel(paginated_list, model);
        model.addAttribute("list_data", paginated_list);
        return "calendar";
    }

}
