package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        model.addAttribute("role", new RoleClient());
        return "role_clients";
    }
}
