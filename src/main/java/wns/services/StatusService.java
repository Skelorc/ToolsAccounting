package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wns.constants.StatusTools;
import wns.dto.StatusToolDTO;
import wns.entity.Owner;
import wns.entity.Status;
import wns.repo.StatusRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatusService implements MainService {
    private final StatusRepo repo;

    public void save(Status status) {
        repo.save(status);
    }

    @Override
    public List<Status> getAll() {
        return (List<Status>) repo.findAll();
    }

    public List<Status> getListByStatuses(StatusTools statusTools) {
        return repo.findAllByStatusTools(statusTools);
    }

    public List<StatusToolDTO> getStatusesByFilter(StatusTools statusTools) {
       return repo.findAllByStatusTools(statusTools).stream()
                .map(StatusToolDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }
}
