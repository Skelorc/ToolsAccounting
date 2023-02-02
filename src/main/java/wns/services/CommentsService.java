package wns.services;/*Author Skelorc*/

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.entity.Comments;
import wns.repo.CommentsRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CommentsService{
    private final CommentsRepo commentsRepo;

    @Transactional(readOnly = true)
    public List<Comments> getAll() {
        return (List<Comments>) commentsRepo.findAll();
    }

    public void delete(long id) {
        commentsRepo.deleteById(id);
    }

    public void save(Comments comments) {
        commentsRepo.save(comments);
    }
}
