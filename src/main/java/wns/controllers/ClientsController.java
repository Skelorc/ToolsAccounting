package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Messages;
import wns.dto.ClientDTO;
import wns.services.ClientsService;
import wns.services.PageableService;
import wns.utils.ResponseHandler;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("clients")
@AllArgsConstructor
public class ClientsController {
    private final ClientsService clientsService;
    private final PageableService pageableService;

    @GetMapping()
    public String showPage(@RequestParam(value = "filter", required = false) String filter,
                                 @RequestParam(value ="page", required = false) Optional<Integer> page,
                                 @RequestParam(value ="size", required = false) Optional<Integer> size,
                                 Model model)
    {
        Page<ClientDTO> paginated_list = clientsService.findPaginated(page,size,filter);
        model.addAttribute("list_clients", paginated_list);
        pageableService.getPageNumbers(paginated_list,model);
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
    public ResponseEntity<Object> create(@RequestBody ClientDTO dto) {
        Messages message = clientsService.saveClient(dto);
        return ResponseHandler.generateResponse(message);
    }


    @GetMapping("/edit/{id}")
    public String showClientForUpdate(@PathVariable("id") long id, Model model) {
        ClientDTO dto = clientsService.findDTOById(id);
        model.addAttribute("client", dto);
        return "client_create";
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Object> updateClient(@PathVariable("id") long id, @RequestBody ClientDTO dto) {
        Messages message = clientsService.updateClient(id, dto);
        return ResponseHandler.generateResponse(message);
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") long id) {
        clientsService.delete(id);
        return "redirect:/clients";
    }
}
