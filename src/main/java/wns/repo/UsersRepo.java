package wns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import wns.entity.User;

public interface UsersRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
