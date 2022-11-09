package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wns.entity.Owner;
import wns.repo.OwnerRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class OwnerService implements MainService{
    private final OwnerRepo ownerRepo;
    @Override
    public List<Owner> getAll() {
        return ownerRepo.findAll();
    }

    public Owner getById(long id)
    {
        return ownerRepo.findById(id).get();
    }

    public void save(Owner owner) {
        ownerRepo.save(owner);
    }

    public Owner findByName(String name) {
        return ownerRepo.findByNameIgnoreCase(name);
    }

    public Owner findById(long id) {
        return ownerRepo.findById(id).get();
    }

    @Override
    public void delete(long id) {
        ownerRepo.deleteById(id);
    }
}
