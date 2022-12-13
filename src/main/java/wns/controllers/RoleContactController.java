package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.entity.RoleContact;
import wns.services.RoleContactService;

@Controller
@RequestMapping("role-contacts")
@AllArgsConstructor
public class RoleContactController {
    private final RoleContactService roleContactService;

    @GetMapping
    public String show(Model model)
    {
        model.addAttribute("list_roles", roleContactService.getAll());
        model.addAttribute("role_contact", new RoleContact());
        return "role_contacts";
    }

    @PostMapping
    public String create(@ModelAttribute("role_contact") RoleContact roleClient)
    {
        roleContactService.save(roleClient);
        return "redirect:/role-contacts";
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("role_contact", roleContactService.getById(id));
        return "edit_role_contacts";
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("role_contact") RoleContact roleClient)
    {
        roleContactService.update(roleClient);
        return "redirect:/role-contacts";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id)
    {
        roleContactService.delete(id);
        return "redirect:/role-contacts";
    }
}
