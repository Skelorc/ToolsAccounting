package wns.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import wns.entity.Comments;

public interface CommentsRepo extends PagingAndSortingRepository<Comments, Long> {
}
