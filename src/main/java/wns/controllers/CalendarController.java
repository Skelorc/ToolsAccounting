package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wns.dto.ClientDTO;
import wns.dto.ToolsDTO;
import wns.services.PageableService;
import wns.services.ToolsService;

import java.util.Optional;

@Controller
@RequestMapping("calendar")
@AllArgsConstructor
public class CalendarController {

private final ToolsService toolsService;
private final PageableService pageableService;

    @GetMapping()
    public String showPage(@RequestParam(value = "filter", required = false) String filter,
                           @RequestParam(value ="page", required = false) Optional<Integer> page,
                           @RequestParam(value ="size", required = false) Optional<Integer> size,
                           Model model)
    {
        Page<ToolsDTO> paginated_list = toolsService.findPaginated(page, size, filter);
        model.addAttribute("list_tools", paginated_list);
        pageableService.addPageNumbersToModel(paginated_list,model);
        return "calendar";
    }
}
