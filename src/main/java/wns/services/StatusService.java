package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.constants.StatusTools;
import wns.dto.StatusToolDTO;
import wns.entity.Owner;
import wns.entity.Status;
import wns.repo.StatusRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional

public class StatusService {
    private final StatusRepo repo;

    public void save(Status status) {
        repo.save(status);
    }

    @Transactional(readOnly = true)
    public List<Status> getAll() {
        return (List<Status>) repo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Status> getListByStatuses(StatusTools statusTools) {
        return repo.findAllByStatusTools(statusTools);
    }

    @Transactional(readOnly = true)
    public List<StatusToolDTO> getStatusesByFilter(StatusTools statusTools) {
       return repo.findAllByStatusTools(statusTools).stream()
                .map(StatusToolDTO::new)
                .collect(Collectors.toList());
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}
