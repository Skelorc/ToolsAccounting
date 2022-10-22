package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wns.constants.StatusTools;
import wns.dto.StatusToolDTO;
import wns.dto.ToolsDTO;
import wns.entity.Status;
import wns.entity.Tools;
import wns.repo.StatusRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class StatusService implements MainService {
    private final StatusRepo repo;

    public void save(Status status) {
        repo.save(status);
    }

    @Override
    public List<Status> getAll() {
        return repo.findAll();
    }

    public List<Status> getListByStatuses(StatusTools statusTools) {
        return repo.findAllByStatusTools(statusTools);
    }

    public List<ToolsDTO> getToolsByStatuses(StatusTools statusTools) {
        return repo.findAllByStatusTools(statusTools)
                .stream().map(x -> new ToolsDTO(x.getTools()))
                .collect(Collectors.toList());
    }

    public List<ToolsDTO> getToolsByStatusesAndProject(StatusTools statusTools, long id) {
        return repo.findAllByStatusTools(statusTools).stream().map(Status::getTools)
                .filter(tools -> tools.getProject().getId() != id)
                .map(ToolsDTO::new)
                .collect(Collectors.toList());
    }


}
