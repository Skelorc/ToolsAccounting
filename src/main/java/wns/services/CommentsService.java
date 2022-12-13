package wns.services;/*Author Skelorc*/

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wns.entity.Comments;
import wns.repo.CommentsRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentsService implements MainService{
    private final CommentsRepo commentsRepo;

    @Override
    public List<Comments> getAll() {
        return (List<Comments>) commentsRepo.findAll();
    }

    @Override
    public void delete(long id) {
        commentsRepo.deleteById(id);
    }
}
