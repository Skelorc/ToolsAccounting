package wns.services;/*Author Skelorc*/

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wns.entity.Contact;
import wns.repo.ContactsRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactsService implements MainService{
    private final ContactsRepo contactsRepo;

    @Override
    public List<Contact> getAll() {
        return (List<Contact>) contactsRepo.findAll();
    }

    @Override
    public void delete(long id) {
        contactsRepo.deleteById(id);
    }
}
