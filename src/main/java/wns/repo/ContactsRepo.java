package wns.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import wns.entity.Contact;

import java.util.List;

public interface ContactsRepo extends PagingAndSortingRepository<Contact, Long> {
    List<Contact> getContactsByRoleContact_Id(long id);
}
