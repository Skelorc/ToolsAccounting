package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wns.constants.Messages;
import wns.constants.TypeClients;
import wns.dto.ClientDTO;
import wns.entity.Client;
import wns.services.ClientsService;
import wns.utils.ResponseHandler;

import java.util.List;

@Controller
@RequestMapping("clients")
@AllArgsConstructor
public class ClientsController {
    private final ClientsService clientsService;

    @GetMapping
    public ModelAndView show(@RequestParam(value = "filter", required=false) String filter)
    {
        List<ClientDTO> list_clients = clientsService.getListByFilter(filter);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("clients",list_clients);
        modelAndView.setViewName("clients");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView creatingClient()
    {
        ClientDTO client = new ClientDTO();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("client",client);
        modelAndView.setViewName("client_create");
        return modelAndView;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ClientDTO dto)
    {
        Messages message = clientsService.saveClient(dto);
        return ResponseHandler.generateResponse(message);
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showClientForUpdate(@PathVariable("id") long id)
    {
        ClientDTO dto = clientsService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("client",dto);
        modelAndView.setViewName("client_create");
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Object> updateClient(@PathVariable("id") long id, @RequestBody ClientDTO dto)
    {
        Messages message = clientsService.updateClient(id,dto);
        return ResponseHandler.generateResponse(message);
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") long id)
    {
        clientsService.delete(id);
        return "redirect:/clients";
    }
}
