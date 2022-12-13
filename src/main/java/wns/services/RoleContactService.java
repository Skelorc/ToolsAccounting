package wns.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wns.entity.RoleContact;
import wns.repo.RoleContactRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleContactService implements MainService {

    private final RoleContactRepo repo;


    public void save(RoleContact roleClient)
    {
        repo.save(roleClient);
    }

    public RoleContact getByRole(String role)
    {
        return repo.findByRole(role);
    }

    @Override
    public  List<RoleContact> getAll() {
        return (List<RoleContact>) repo.findAll();
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }

    public RoleContact getById(long roleClientId) {
        return repo.findById(roleClientId).get();
    }

    public void update(RoleContact roleClient) {
        RoleContact roleClient1 = repo.findById(roleClient.getId()).get();
        roleClient1.setRole(roleClient.getRole());
        repo.save(roleClient1);
    }
}
