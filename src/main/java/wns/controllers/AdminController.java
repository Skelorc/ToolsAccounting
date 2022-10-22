package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wns.constants.Messages;
import wns.constants.Roles;
import wns.dto.UserDTO;
import wns.entity.User;
import wns.services.UserService;

import java.util.List;

@Controller
@RequestMapping("admin-panel")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    public String showRegistrationPage(@ModelAttribute("message") String message,Model model)
    {
        List<UserDTO> allUsers = userService.getAll();
        model.addAttribute("roles", Roles.values());
        model.addAttribute("users",allUsers);
        model.addAttribute("user", new User());
        model.addAttribute("message", message);
        return "admin_panel";
    }

    @PostMapping("/create")
    public String saveNewUser(@ModelAttribute User user)
    {
        userService.saveUser(user);
        return "redirect:/admin-panel";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("roles", Roles.values());
        model.addAttribute("user",userService.getUserByID(id));
        return "edit_user";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@ModelAttribute User user)
    {
        userService.updateUser(user);
        return "redirect:/admin-panel";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id){
        userService.deleteUser(id);
        return "redirect:/admin-panel";
    }
}
