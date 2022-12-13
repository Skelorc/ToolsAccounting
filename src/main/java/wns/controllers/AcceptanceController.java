package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wns.constants.Filter;
import wns.dto.PageDataDTO;
import wns.entity.Tools;
import wns.services.ClientsService;
import wns.services.PageableFilterService;
import wns.services.ProjectService;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("acceptance")
public class AcceptanceController {

    private final ProjectService projectService;
    private final ClientsService clientsService;
    private final PageableFilterService pageableFilterService;

    @GetMapping
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       @RequestParam(value = "filter", required = false) Filter filter,
                       Model model) {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, filter));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("list_projects", paginated_list);
        return "acceptance";
    }

    @GetMapping("/create/{id}")
    public String showClose(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("project",projectService.getById(id));
        model.addAttribute("tool",new Tools());
        model.addAttribute("clients",clientsService.getAll());
        return "acceptance_close";
    }
}
