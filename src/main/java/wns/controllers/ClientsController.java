package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wns.entity.Client;
import wns.services.ClientService;

import java.util.List;

@Controller
@RequestMapping("clients")
@AllArgsConstructor
public class ClientsController {
    private final ClientService clientService;

    @GetMapping
    public ModelAndView show()
    {
        List<Client> all = clientService.getAll();
        Client client = new Client();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("clients",all);
        modelAndView.addObject("client",client);
        modelAndView.setViewName("clients");
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Client client, Model model)
    {
        String answer = clientService.saveClient(client);
        model.addAttribute("answer",answer);
        return "redirect:/clients";
    }

}
