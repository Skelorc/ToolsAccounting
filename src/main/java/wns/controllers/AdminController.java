package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wns.constants.Messages;
import wns.constants.Roles;
import wns.dto.UserDTO;
import wns.services.UserService;
import wns.utils.ResponseHandler;

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
        model.addAttribute("user", new UserDTO());
        model.addAttribute("message", message);
        return "admin_panel";
    }

    @PostMapping("/create")
    public String saveNewUser(@ModelAttribute UserDTO user)
    {
        userService.saveUser(user);
        return "redirect:/admin-panel";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model)
    {
        UserDTO dto = userService.getDTOByID(id);
        model.addAttribute("roles", Roles.values());
        model.addAttribute("user",dto);
        return "edit_user";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@ModelAttribute UserDTO user, RedirectAttributes redirectAttributes)
    {
        Messages messages = userService.updateUser(user);
        redirectAttributes.addFlashAttribute("message",messages.getValue());
        return "redirect:/admin-panel";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id){
        userService.deleteUser(id);
        return "redirect:/admin-panel";
    }
}
