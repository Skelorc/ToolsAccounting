package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wns.constants.Roles;
import wns.entity.User;
import wns.entity.UserDTO;
import wns.services.UserService;

import javax.validation.Valid;
import java.util.Arrays;

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

  /*  @PostMapping
    @RequestMapping("/registration-page/reg")
    public String registration_retail_users(@ModelAttribute("user_dto") @Valid UserDTO userDTO, Model model)
    {
        String isExists = serviceRegistration.checkEmail(userDTO);
        model.addAttribute("message",isExists);
        return "registration-page";
    }*/


}
