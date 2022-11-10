package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wns.entity.Category;
import wns.entity.Owner;
import wns.repo.CategoryRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService implements MainService {

    private final CategoryRepo categoryRepo;

    @Override
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    public Category getById(long id)
    {
        return categoryRepo.findById(id).get();
    }

    public void save(Category category) {
        categoryRepo.save(category);
    }

    public Category findByName(String name) {
        return categoryRepo.findByNameIgnoreCase(name);
    }

    @Override
    public void delete(long id) {
        categoryRepo.deleteById(id);
    }


}
