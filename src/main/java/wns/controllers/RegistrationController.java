package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wns.constants.Roles;
import wns.entity.UserDTO;
import wns.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("registration")
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public ModelAndView showRegistrationPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new UserDTO());
        modelAndView.addObject("roles", Roles.values());
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping
    public String saveNewUser(@ModelAttribute("user") @Valid UserDTO userDTO, Model model)
    {
        String message = userService.saveUser(userDTO);
        model.addAttribute("message",message);
        return "redirect:/registration";
    }
}
