package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wns.repo.ProjectRepo;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepo projectRepo;

}
