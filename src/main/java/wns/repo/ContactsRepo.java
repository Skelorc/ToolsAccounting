package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.entity.Contact;

import java.util.List;

public interface ContactsRepo extends CrudRepository<Contact, Long> {
    List<Contact> getContactsByRoleContact_Id(long id);
}
