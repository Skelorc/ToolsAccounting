package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wns.aspects.ToLog;
import wns.entity.Owner;
import wns.repo.OwnerRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class OwnerService implements MainService {
    private final OwnerRepo ownerRepo;

    @Override
    public List<Owner> getAll() {
        return (List<Owner>) ownerRepo.findAll();
    }

    public Owner getById(long id) {
        return ownerRepo.findById(id).get();
    }

    @ToLog
    public void save(Owner owner) {
        if (!ownerRepo.existsById(owner.getId()))
            ownerRepo.save(owner);
        else {
            Owner old_owner = ownerRepo.findById(owner.getId()).get();
            old_owner.setName(owner.getName());
            old_owner.setCode(owner.getCode());
            old_owner.setTools(owner.getTools());
            ownerRepo.save(old_owner);
        }
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
