package wns.controllers;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Filter;
import wns.dto.EstimateNameDTO;
import wns.dto.PageDataDTO;
import wns.entity.Category;
import wns.entity.EstimateName;
import wns.services.CategoryService;
import wns.services.EstimateNameService;
import wns.services.PageableFilterService;

import java.util.Optional;

@Controller
@RequestMapping("/estimate-name")
@AllArgsConstructor
public class EstimateNameController {
    private final EstimateNameService estimateNameService;
    private final PageableFilterService pageableFilterService;
    private final CategoryService categoryService;

    @GetMapping()
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model) {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.ESTIMATE_NAME));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("list_estimates", paginated_list);
        model.addAttribute("list_categories",categoryService.getAll());
        model.addAttribute("estimateName", new EstimateNameDTO());
        return "estimate_name";
    }

    @PostMapping
    public String create(@ModelAttribute("estimateName") EstimateNameDTO estimateName)
    {
        Category category = categoryService.findById(estimateName.getCategoryId());
        estimateNameService.save(estimateName,category);
        return "redirect:/estimate-name";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable long id, Model model)
    {
        model.addAttribute("list_categories",categoryService.getAll());
        model.addAttribute("estimateName",estimateNameService.getNameEstimateById(id));
        return "edit_estimate_name";
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("estimateName") EstimateNameDTO estimateName)
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
