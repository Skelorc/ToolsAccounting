package wns.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wns.constants.Filter;
import wns.dto.PageDataDTO;
import wns.entity.Contact;
import wns.entity.RoleContact;
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
        model.addAttribute("list_contacts", paginated_list);
        model.addAttribute("roles_contacts", roleContactService.getAll());
        return "contacts";
    }

    @GetMapping("/filter")
    public String filterContacts(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                 @RequestParam(value = "size", required = false) Optional<Integer> size,
                                 @RequestParam(value = "filter", required = false) long roleContactId,
                                 Model model) {
        Page<Object> paginated_list = pageableFilterService.getListData(new PageDataDTO(page, size, Filter.CONTACTS_BY_ROLE,roleContactId));
        pageableFilterService.addPageNumbers(paginated_list, model);
        model.addAttribute("list_contacts", paginated_list);
        model.addAttribute("roles_contacts", roleContactService.getAll());
        return "contacts";
    }

    @GetMapping("/create")
    public String show(Model model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("roles_contacts", roleContactService.getAll());
        return "create_contact";
    }

    @PostMapping("/create")
    public String createContact(@ModelAttribute("contact") Contact contact,
                                @RequestParam("comment") String comment,
                                @RequestParam("photos") String photos) {
        contactsService.saveContact(contact, comment, photos);
        return "redirect:/contacts";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("contact", contactsService.findById(id));
        model.addAttribute("roles_contacts", roleContactService.getAll());
        return "edit_contact";
    }

    @PostMapping("/edit")
    public String updateContact(@ModelAttribute("contact") Contact contact,
                                @RequestParam("comment") String comment,
                                @RequestParam("photos") String photos) {
        System.out.println(contact);
        System.out.println(comment);
        System.out.println(photos);
        //contactsService.saveContact(contact, comment,photos);
        return "redirect:/contacts";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        contactsService.delete(id);
        return "redirect:/contacts";
    }
}
