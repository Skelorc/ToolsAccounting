package wns.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import wns.entity.User;

import java.util.List;

public interface UsersRepo extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);
    @Query("select fullName from User where roles = 'WORKER'")
    List<String> findAllWorkers();
    @Query("select fullName from User where roles = 'MANAGER'")
    List<String> findAllManagers();
}
