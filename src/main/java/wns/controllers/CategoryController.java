package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Filter;
import wns.dto.PageDataDTO;
import wns.entity.Category;
import wns.services.CategoryService;
import wns.services.PageableFilterService;

import java.util.Optional;

@Controller
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final PageableFilterService pageableFilterService;

    @GetMapping
    public String show(@RequestParam(value = "page", required = false) Optional<Integer> page,
                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                       Model model)
    {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.CATEGORY));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("list_category",paginated_list);
        model.addAttribute("category",new Category());
        return "category";
    }

    @PostMapping
    public String create(@ModelAttribute Category category)
    {
        categoryService.save(category);
        return "redirect:/category";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("category",categoryService.getById(id));
        return "edit_category";
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute Category category)
    {
        categoryService.save(category);
        return "redirect:/category";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id)
    {
        categoryService.delete(id);
        return "redirect:/category";
    }

}
