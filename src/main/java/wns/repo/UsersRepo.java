package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.entity.User;

public interface UsersRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
