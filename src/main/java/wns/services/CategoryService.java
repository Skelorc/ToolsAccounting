package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.entity.Category;
import wns.entity.Owner;
import wns.repo.CategoryRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepo categoryRepo;

    @Transactional(readOnly = true)
    public List<Category> getAll() {
        return (List<Category>) categoryRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Category getById(long id)
    {
        return categoryRepo.findById(id).get();
    }

    public void save(Category category) {
        categoryRepo.save(category);
    }

    @Transactional(readOnly = true)
    public Category findByName(String name) {
        return categoryRepo.findByNameIgnoreCase(name);
    }

    public void delete(long id) {
        categoryRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Category findById(long categoryId) {
        return categoryRepo.findById(categoryId).get();
    }
}
