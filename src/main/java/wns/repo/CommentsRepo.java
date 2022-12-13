package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.entity.Comments;

public interface CommentsRepo extends CrudRepository<Comments, Long> {
}
