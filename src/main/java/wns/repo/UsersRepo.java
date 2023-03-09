package wns.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import wns.entity.User;

public interface UsersRepo extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);
}
