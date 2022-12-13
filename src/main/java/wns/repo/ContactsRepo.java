package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.entity.Contact;

public interface ContactsRepo extends CrudRepository<Contact, Long> {
}
