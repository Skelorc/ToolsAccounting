package wns.services;/*Author Skelorc*/

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.entity.Comments;
import wns.entity.Contact;
import wns.repo.ContactsRepo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class ContactsService implements MainService{
    private final ContactsRepo contactsRepo;
    private final CommentsService commentsService;
    @Override
    public List<Contact> getAll() {
        return (List<Contact>) contactsRepo.findAll();
    }

    @Override
    public void delete(long id) {
        contactsRepo.deleteById(id);
    }

    @Transactional
    public void saveContact(Contact contact, String comment, String photos) {
        contact.setPhotos(new HashSet<>(Arrays.asList(photos.split(","))));
        contactsRepo.save(contact);
        Comments comments = new Comments();
        comments.setCreated(LocalDate.now());
        comments.setText(comment);
        comments.setNameOfCommentator(SecurityContextHolder.getContext().getAuthentication().getName());
        comments.setContact(contact);
        commentsService.save(comments);
    }
}
