package wns.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import wns.entity.Comment;

import java.util.Optional;

public interface CommentsRepo extends PagingAndSortingRepository<Comment, Long> {

    Optional<Comment> findByToolsId(long id);
}
