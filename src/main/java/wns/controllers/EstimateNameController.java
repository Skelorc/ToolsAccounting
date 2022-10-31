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
import wns.dto.EstimateNameDTO;
import wns.services.EstimateNameService;
import wns.services.PageableFilterService;

import java.util.Optional;

@Controller
@RequestMapping("/estimate-name")
@AllArgsConstructor
public class EstimateNameController {
    private final EstimateNameService estimateNameService;
    private final PageableFilterService pageableFilterService;

    @GetMapping()
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model) {
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size, Filter.ESTIMATE_NAME, PaginationConst.TOOLS,-1);
        pageableFilterService.addPageNumbersToModel(paginated_list, model);
        model.addAttribute("list_tools", paginated_list);
        model.addAttribute("estimateNameDTO", new EstimateNameDTO());
        model.addAttribute("project_id", -1);
        return "tools";
    }

}
