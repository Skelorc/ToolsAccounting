package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.aspects.ToLog;
import wns.dto.ContactDTO;
import wns.entity.Comment;
import wns.entity.Contact;
import wns.entity.RoleContact;
import wns.repo.ContactsRepo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ContactsService {
    private final ContactsRepo contactsRepo;

    @Transactional(readOnly = true)
    public List<Contact> getAll() {
        return (List<Contact>) contactsRepo.findAll();
    }

    public void delete(long id) {
        contactsRepo.deleteById(id);
    }

    @ToLog
    public void saveContact(ContactDTO dto, RoleContact roleContact, String photos, Comment comment) {
        Optional<Contact> byName = contactsRepo.findByName(dto.getName());
        if(byName.isEmpty()) {
            Contact contact = new Contact();
            contact.setName(dto.getName());
            contact.setIssuedBy(dto.getIssuedBy());
            contact.setDateIssuePassport(dto.getDateIssuePassport());
            contact.setNumberPassport(dto.getNumberPassport());
            contact.setPhotos(new HashSet<>(Arrays.asList(photos.split(","))));
            contact.setRoleContact(roleContact);
            contact.setComment(comment);
            comment.setContact(contact);
            contactsRepo.save(contact);
        }
    }

    @Transactional(readOnly = true)
    public Contact findById(long id) {
        return contactsRepo.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Contact> getContactByRoleId(long id) {
        return contactsRepo.getContactsByRoleContact_Id(id);
    }

    public Contact update(ContactDTO dto, String photos, RoleContact role) {
        Contact contact = contactsRepo.findById(dto.getId()).orElseThrow(NullPointerException::new);
        contact.setPhotos(new HashSet<>(Arrays.asList(photos.split(","))));
        contact.setIssuedBy(dto.getIssuedBy());
        contact.setDateIssuePassport(dto.getDateIssuePassport());
        contact.setNumberPassport(dto.getNumberPassport());
        contact.setName(dto.getName());
        contact.setRoleContact(role);
        return contactsRepo.save(contact);
    }
}
