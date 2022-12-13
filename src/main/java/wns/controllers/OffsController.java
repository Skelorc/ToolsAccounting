package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wns.constants.Filter;
import wns.dto.PageDataDTO;
import wns.dto.StatusToolDTO;
import wns.services.ClientsService;
import wns.services.PageableFilterService;

import java.util.Optional;

@Controller
@RequestMapping("write-off")
@AllArgsConstructor
public class OffsController {
    private final ClientsService clientsService;
    private final PageableFilterService pageableFilterService;

    @GetMapping
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model) {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.WRITEOFF));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("list_statuses", paginated_list);
        return "write_off";
    }

    @GetMapping("/create")
    public String create(@RequestParam(value = "page", required = false) Optional<Integer> page,
                         @RequestParam(value = "size", required = false) Optional<Integer> size,
                         @ModelAttribute("message") String message,
                         Model model)
    {
        Page<Object> paginated = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.WAITING));
        pageableFilterService.addPageNumbers(paginated, model);
        model.addAttribute("clients", clientsService.getAll());
        model.addAttribute("list_tools", paginated);
        model.addAttribute("status", new StatusToolDTO());
        model.addAttribute("message", message);
        return "create_write_off";
    }
}
