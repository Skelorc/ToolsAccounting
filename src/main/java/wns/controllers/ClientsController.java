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
import wns.services.RoleClientService;
import wns.utils.ResponseHandler;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("clients")
@AllArgsConstructor
public class ClientsController {
    private final ClientsService clientsService;
    private final PageableFilterService pageableFilterService;
    private final RoleClientService roleClientService;

    @GetMapping()
    public String showPage(@RequestParam(value = "page", required = false) Optional<Integer> page,
                           @RequestParam(value = "size", required = false) Optional<Integer> size,
                           Model model) {
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size, Filter.ALL_CLIENTS, PaginationConst.CLIENT, -1);
        pageableFilterService.addPageNumbersToModel(paginated_list, model);
        model.addAttribute("list_clients", paginated_list);
        return "clients";
    }

    @PostMapping()
    public String findClientsByName(@RequestParam String username, Model model) {
        List<ClientDTO> listByName = clientsService.findListByName(username);
        model.addAttribute("list_clients", listByName);
        return "clients";
    }


    @GetMapping("/create")
    public String creatingClient(Model model) {
        model.addAttribute("client", new ClientDTO());
        model.addAttribute("roles_client", roleClientService.getAll());
        return "client_create";
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody Client client) {
        System.out.println(client);
        //Messages message = clientsService.saveClient(client);
        return ResponseHandler.generateResponse(Messages.OK);
    }


    @GetMapping("/edit/{id}")
    public String showClientForUpdate(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                      @RequestParam(value = "size", required = false) Optional<Integer> size,
                                      @PathVariable("id") long id, Model model) {
        Client client = clientsService.getById(id);
        Page<Object> paginated_list = pageableFilterService.getPageByFilter(page, size, Filter.PROJECTS_BY_CLIENTS, PaginationConst.CLIENT, id);
        pageableFilterService.addPageNumbersToModel(paginated_list, model);
        model.addAttribute("client", client);
        model.addAttribute("list_projects", paginated_list);
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
