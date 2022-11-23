package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.entity.RoleClient;
import wns.services.RoleClientService;

@Controller
@RequestMapping("role-clients")
@AllArgsConstructor
public class RoleClientController {
    private final RoleClientService roleClientService;

    @GetMapping
    public String show(Model model)
    {
        model.addAttribute("list_roles",roleClientService.getAll());
        model.addAttribute("role_client", new RoleClient());
        return "role_clients";
    }

    @PostMapping
    public String create(@ModelAttribute("role") RoleClient roleClient)
    {
        roleClientService.save(roleClient);
        return "redirect:/role-clients";
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("role_client",roleClientService.getById(id));
        return "edit_role_clients";
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("role_client") RoleClient roleClient)
    {
        roleClientService.update(roleClient);
        return "redirect:/role-clients";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id)
    {
        roleClientService.delete(id);
        return "redirect:/role-clients";
    }
}
