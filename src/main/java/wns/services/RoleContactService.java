package wns.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.entity.RoleContact;
import wns.repo.RoleContactRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class RoleContactService {

    private final RoleContactRepo repo;

    public void save(RoleContact roleClient)
    {
        repo.save(roleClient);
    }

    @Transactional(readOnly = true)
    public RoleContact getByRole(String role)
    {
        return repo.findByRole(role);
    }

    @Transactional(readOnly = true)
    public  List<RoleContact> getAll() {
        return (List<RoleContact>) repo.findAll();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public RoleContact getById(long roleClientId) {
        return repo.findById(roleClientId).get();
    }

    public void update(RoleContact roleClient) {
        RoleContact roleClient1 = repo.findById(roleClient.getId()).get();
        roleClient1.setRole(roleClient.getRole());
        repo.save(roleClient1);
    }
}
