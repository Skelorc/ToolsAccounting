package wns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import wns.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByNameIgnoreCase(String name);
}
