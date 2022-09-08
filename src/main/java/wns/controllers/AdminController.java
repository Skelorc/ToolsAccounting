package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wns.constants.Messages;
import wns.constants.Roles;
import wns.dto.UserDTO;
import wns.services.UserService;
import wns.utils.ResponseHandler;

import java.util.List;

@Controller
@RequestMapping("admin_panel")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    public ModelAndView showRegistrationPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        List<UserDTO> allUsers = userService.getAllUsers();
        modelAndView.addObject("roles", Roles.values());
        modelAndView.addObject("users",allUsers);
        modelAndView.setViewName("admin_panel");
        return modelAndView;
    }

    @PostMapping
    public ResponseEntity<Object> saveNewUser(@RequestBody UserDTO user)
    {
        Messages message = userService.saveUser(user);
        return ResponseHandler.generateResponse(message);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") long id)
    {
        UserDTO dto = userService.getDTOByID(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roles", Roles.values());
        modelAndView.addObject("user",dto);
        modelAndView.setViewName("edit_user");
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO user)
    {
        Messages message = userService.updateUser(user);
        return ResponseHandler.generateResponse(message);
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id){
        userService.deleteUser(id);
        return "redirect:/admin_panel";
    }
}
