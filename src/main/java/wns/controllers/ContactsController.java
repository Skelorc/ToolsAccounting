package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wns.constants.Filter;
import wns.dto.PageDataDTO;
import wns.entity.Contact;
import wns.services.ContactsService;
import wns.services.PageableFilterService;
import wns.services.RoleContactService;

import java.util.Optional;

@Controller
@RequestMapping("contacts")
@AllArgsConstructor
public class ContactsController {

    private final ContactsService contactsService;
    private final PageableFilterService pageableFilterService;
    private final RoleContactService roleContactService;

    @GetMapping()
    public String showPage(@RequestParam(value = "page", required = false) Optional<Integer> page,
                           @RequestParam(value = "size", required = false) Optional<Integer> size,
                           Model model) {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.ALL_CONTACTS));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("list_clients", paginated_list);
        return "contacts";
    }

    @GetMapping("/create")
    public String show(Model model)
    {
        model.addAttribute("contact", new Contact());
        model.addAttribute("roles_contacts", roleContactService.getAll());
        return "create_contact";
    }
}
