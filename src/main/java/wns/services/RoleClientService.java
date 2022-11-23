package wns.services;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import wns.entity.RoleClient;
import wns.repo.RoleClientRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleClientService implements MainService {

    private final RoleClientRepo repo;


    public void save(RoleClient roleClient)
    {
        repo.save(roleClient);
    }

    public RoleClient getByRole(String role)
    {
        return repo.findByRole(role);
    }

    @Override
    public  List<RoleClient> getAll() {
        return (List<RoleClient>) repo.findAll();
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }

    public RoleClient getById(long roleClientId) {
        return repo.findById(roleClientId).get();
    }
}
