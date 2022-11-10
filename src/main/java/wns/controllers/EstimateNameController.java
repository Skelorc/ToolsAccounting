package wns.controllers;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Filter;
import wns.constants.PaginationConst;
import wns.dto.EstimateNameDTO;
import wns.entity.EstimateName;
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
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size, Filter.ESTIMATE_NAME, PaginationConst.ESTIMATE_NAME,-1);
        pageableFilterService.addPageNumbersToModel(paginated_list, model);
        model.addAttribute("list_estimates", paginated_list);
        model.addAttribute("estimateName", new EstimateName());
        return "estimate_name";
    }

    @PostMapping
    public String create(@ModelAttribute("estimateName") EstimateName estimateName)
    {
        estimateNameService.save(estimateName);
        return "redirect:/estimate-name";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable long id, Model model)
    {
        model.addAttribute("estimateName",estimateNameService.getNameEstimateById(id));
        return "estimate_name_edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("estimateName") EstimateName estimateName)
    {
        estimateNameService.save(estimateName);
        return "redirect:/estimate-name";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id)
    {
        estimateNameService.delete(id);
        return "redirect:/estimate-name";
    }

}
