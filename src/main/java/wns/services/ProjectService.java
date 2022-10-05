package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import wns.constants.StatusTools;
import wns.dto.ClientDTO;
import wns.dto.ProjectDTO;
import wns.dto.ToolsDTO;
import wns.entity.Client;
import wns.entity.Project;
import wns.entity.Status;
import wns.entity.Tools;
import wns.repo.ProjectRepo;
import wns.repo.ToolsRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService implements MainService{
    private final ProjectRepo projectRepo;
    private final ToolsService toolsService;
    private final EstimateNameService estimateNameService;
    private final ClientsService clientsService;
    private final ModelMapper modelMapper;

    @Override
    public List<Project> getAll() {
        return projectRepo.findAll();
    }

    public Project createProject(ProjectDTO projectDTO) {
        Project project = projectRepo.findByName(projectDTO.getName());
        if(project==null)
        {
            project = new Project();
            project.setName(projectDTO.getName());
            project.setNumber(projectDTO.getNumber());
            project.setStatus(projectDTO.getStatus());
            project.setTypeLease(projectDTO.getTypeLease());
            project.setQuantity(projectDTO.getQuantity());
            project.setCreated(projectDTO.getCreated());
            project.setEmployee(SecurityContextHolder.getContext().getAuthentication().getName());
            project.setStart(projectDTO.getStart());
            project.setEnd(projectDTO.getEnd());
            ClientDTO client_dto = clientsService.findDTOById(projectDTO.getClient_id());
            client_dto.getProjects().add(project);
            clientsService.saveClient(client_dto);
            project.setClient(modelMapper.map(client_dto,Client.class));
            project.setPhoneNumber(client_dto.getPhoneNumber());
            project.setPhotos(projectDTO.getPhotos());
            project.setDiscount(projectDTO.getDiscount());
            project.setNote(projectDTO.getNote());
            project.setSum(projectDTO.getSum());
            project.setFinalSumUsn(projectDTO.getFinalSumUsn());
            project.setPriceTools(projectDTO.getPriceTools());
            project.setDiscountByProject(projectDTO.getDiscountByProject());
            project.setSumWithDiscount(projectDTO.getSumWithDiscount());
            project.setReceived(projectDTO.getReceived());
            project.setRemainder(projectDTO.getRemainder());
            for (Long id : projectDTO.getTools_id()) {
                Tools tool = modelMapper.map(toolsService.findById(id), Tools.class);
                Status status = tool.getStatus();
                status.setCreated(project.getCreated());
                status.setStart(project.getStart());
                status.setEnd(project.getEnd());
                status.setStatusTools(StatusTools.ONLEASE);
                status.setEmployee(SecurityContextHolder.getContext().getAuthentication().getName());
                status.setExecutor(client_dto.getFullName());
                status.setNote(project.getNote());
                status.setPhone_number(client_dto.getPhoneNumber());
                status.setPhotos(projectDTO.getPhotos());
                project.getTools().add(tool);
                tool.setProject(project);
                toolsService.save(tool);
            }
            projectRepo.save(project);
            return project;
        }
       return project;
    }
}
