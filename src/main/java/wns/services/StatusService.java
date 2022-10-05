package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wns.constants.StatusTools;
import wns.dto.StatusToolDTO;
import wns.entity.Status;
import wns.entity.Tools;
import wns.repo.StatusRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatusService implements MainService{
    private final StatusRepo repo;
    private final PageableService pageableService;

    public void save(Status status)
    {
        repo.save(status);
    }

    @Override
    public List<Status> getAll() {
        return repo.findAll();
    }

    public List<Status> getListByStatuses(StatusTools statusTools)
    {
        return repo.findAllByStatusTools(statusTools);
    }

    public Page<Status> findPaginated(Optional<Integer> page, Optional<Integer> size, List<Status> list)
    {
        return pageableService.findPaginated(page, size, list);
    }


}
