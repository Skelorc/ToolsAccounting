package wns.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import wns.entity.Contact;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ContactsRepo extends PagingAndSortingRepository<Contact, Long> {
    List<Contact> getContactsByRoleContact_Id(long id);

    Optional<Contact> findByName(String name);
}
