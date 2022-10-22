package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Filter;
import wns.constants.Messages;
import wns.constants.PaginationConst;
import wns.dto.ClientDTO;
import wns.entity.Client;
import wns.services.PageableFilterService;
import wns.services.ClientsService;
import wns.utils.ResponseHandler;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("clients")
@AllArgsConstructor
public class ClientsController {
    private final ClientsService clientsService;
    private final PageableFilterService pageableFilterService;

    @GetMapping()
    public String showPage(@RequestParam(value = "filter", required = false) Filter filter,
                                 @RequestParam(value ="page", required = false) Optional<Integer> page,
                                 @RequestParam(value ="size", required = false) Optional<Integer> size,
                                 Model model)
    {
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page,size,filter, PaginationConst.CLIENT,0);
        pageableFilterService.addPageNumbersToModel(paginated_list,model);
        model.addAttribute("list_clients", paginated_list);
        return "clients";
    }

    @PostMapping()
    public String findClientsByName(@RequestParam String username, Model model)
    {
        List<ClientDTO> listByName = clientsService.findListByName(username);
        model.addAttribute("list_clients",listByName);
        return "clients";
    }


    @GetMapping("/create")
    public String creatingClient(Model model) {
        ClientDTO client = new ClientDTO();
        model.addAttribute("client", client);
        return "client_create";
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody Client client) {
        Messages message = clientsService.saveClient(client);
        return ResponseHandler.generateResponse(message);
    }


    @GetMapping("/edit/{id}")
    public String showClientForUpdate(@PathVariable("id") long id, Model model) {
        Client client = clientsService.getById(id);
        model.addAttribute("client", client);
        return "client_create";
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Object> updateClient(@PathVariable("id") long id, @RequestBody Client client) {
        clientsService.updateClient(id, client);
        return ResponseHandler.generateResponse(Messages.OK);
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") long id) {
        clientsService.delete(id);
        return "redirect:/clients";
    }
}
