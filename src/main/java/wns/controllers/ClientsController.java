package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wns.constants.TypeClients;
import wns.entity.Client;
import wns.services.ClientsService;

import java.util.List;

@Controller
@RequestMapping("clients")
@AllArgsConstructor
public class ClientsController {
    private final ClientsService clientsService;

    @GetMapping
    public ModelAndView show()
    {
        List<Client> all = clientsService.getAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("clients",all);
        modelAndView.setViewName("clients");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView creatingClient()
    {
        List<Client> all = clientsService.getAll();
        Client client = new Client();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("clients",all);
        modelAndView.addObject("client",client);
        modelAndView.setViewName("clientCreate");
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Client client, Model model)
    {
        String answer = clientsService.saveClient(client);
        model.addAttribute("answer",answer);
        return "redirect:/clientCreate";
    }

}
