package wns.services;

import org.springframework.stereotype.Service;
import wns.repo.ProjectRepo;

@Service
public class ProjectService {

    private final ProjectRepo projectRepo;

    public ProjectService(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }
}
