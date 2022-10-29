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

import java.util.Optional;

@Controller
@RequestMapping("calendar")
@AllArgsConstructor
public class CalendarController {
private final PageableFilterService pageableFilterService;

    @GetMapping()
    public String showPage(@RequestParam(value = "filter", required = false) Filter filter,
                           @RequestParam(value ="page", required = false) Optional<Integer> page,
                           @RequestParam(value ="size", required = false) Optional<Integer> size,
                           Model model)
    {
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size, filter, PaginationConst.TOOLS,-1);
        model.addAttribute("list_tools", paginated_list);
        pageableFilterService.addPageNumbersToModel(paginated_list,model);
        return "calendar";
    }
}
