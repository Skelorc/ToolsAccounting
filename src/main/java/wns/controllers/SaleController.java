package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Filter;
import wns.constants.PaginationConst;
import wns.dto.StatusToolDTO;
import wns.services.ClientsService;
import wns.services.PageableFilterService;

import java.util.Optional;

@Controller
@RequestMapping("sale")
@AllArgsConstructor
public class SaleController {

    private final ClientsService clientsService;
    private final PageableFilterService pageableFilterService;

    @GetMapping
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model) {
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size, Filter.SALE,PaginationConst.STATUS,-1);
        pageableFilterService.addPageNumbersToModel(paginated_list, model);
        model.addAttribute("list_statuses", paginated_list);
        return "sale";
    }

    @GetMapping("/create")
    public String create(@RequestParam(value = "page", required = false) Optional<Integer> page,
                         @RequestParam(value = "size", required = false) Optional<Integer> size,
                         Model model) {
        Page<Object> paginated = pageableFilterService.getPageByFilter(page, size, Filter.WAITING, PaginationConst.TOOLS,-1);
        pageableFilterService.addPageNumbersToModel(paginated, model);
        model.addAttribute("clients", clientsService.getAll());
        model.addAttribute("list_tools", paginated);
        model.addAttribute("status", new StatusToolDTO());
        return "sale_create";
    }
}
