package wns.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import wns.entity.Category;

public interface CategoryRepo extends PagingAndSortingRepository<Category, Long> {
    Category findByNameIgnoreCase(String name);
}
