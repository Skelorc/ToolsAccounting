package wns.repo;

import org.springframework.data.repository.CrudRepository;
import wns.entity.Category;

public interface CategoryRepo extends CrudRepository<Category, Long> {
    Category findByNameIgnoreCase(String name);
}
