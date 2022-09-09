package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("clients")
@AllArgsConstructor
public class ClientsController {
    private final ClientsService clientsService;

    @GetMapping()
    public ModelAndView showPage(@RequestParam(value = "filter", required = false) String filter,
                                 @RequestParam(value ="page", required = false) Optional<Integer> page,
                                 @RequestParam(value ="size", required = false) Optional<Integer> size)
    {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(100);
        Page<ClientDTO> paginated_list = clientsService.findPaginated(PageRequest.of(currentPage - 1, pageSize),filter);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list_clients", paginated_list);
        int totalPages = paginated_list.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("page_numbers", pageNumbers);
        }
        modelAndView.setViewName("clients");
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView findClientsByName(@RequestParam String username)
    {
        List<ClientDTO> listByName = clientsService.findListByName(username);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list_clients",listByName);
        modelAndView.setViewName("clients");
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView creatingClient() {
        ClientDTO client = new ClientDTO();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("client", client);
        modelAndView.setViewName("client_create");
        return modelAndView;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ClientDTO dto) {
        Messages message = clientsService.saveClient(dto);
        return ResponseHandler.generateResponse(message);
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showClientForUpdate(@PathVariable("id") long id) {
        ClientDTO dto = clientsService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("client", dto);
        modelAndView.setViewName("client_create");
        return modelAndView;
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
