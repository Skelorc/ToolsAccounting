package wns.controllers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.entity.Owner;
import wns.services.OwnerService;

@Controller
@RequestMapping("owners")
@AllArgsConstructor
public class OwnersController {
    private final OwnerService ownerService;

    @GetMapping
    public String show(Model model)
    {
        model.addAttribute("list_owner",ownerService.getAll());
        model.addAttribute("owner",new Owner());
        return "owners";
    }

    @PostMapping
    public String create(@ModelAttribute("owner") Owner owner)
    {
        ownerService.save(owner);
        return "redirect:/owners";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("owner",ownerService.findById(id));
        return "owners_edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute Owner owner)
    {
        ownerService.save(owner);
        return "redirect:/owners";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id)
    {
        ownerService.delete(id);
        return "redirect:/owners";
    }


}
