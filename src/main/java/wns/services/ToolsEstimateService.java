package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import wns.dto.ToolsEstimateDTO;
import wns.entity.Estimate;
import wns.entity.Project;
import wns.entity.Tools;
import wns.entity.ToolsEstimate;
import wns.repo.ToolsEstimateRepo;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ToolsEstimateService implements MainService{
    private final ToolsEstimateRepo toolsEstimateRepo;
    private final ModelMapper modelMapper;

    @Override
    public  List<ToolsEstimate> getAll() {
        return toolsEstimateRepo.findAll();
    }

    public void save(ToolsEstimate toolsEstimate)
    {
        toolsEstimateRepo.save(toolsEstimate);
    }

    public void saveList(List<ToolsEstimate> list_tools_estimates) {

        list_tools_estimates.forEach(toolsEstimateRepo::save);
    }

    public void delete(ToolsEstimate toolsEstimate)
    {
        toolsEstimateRepo.delete(toolsEstimate);
    }

    public void addToolEstimateToEstimate(Project project, Tools tool) {
        Estimate estimate = project.getEstimate();
        ToolsEstimateDTO dto = new ToolsEstimateDTO(tool,estimate);
        toolsEstimateRepo.save(modelMapper.map(dto, ToolsEstimate.class));
    }

    public void deleteToolEstimateFromEstimate(Tools tool) {
        Project project = tool.getProject();
        Estimate estimate = project.getEstimate();
        estimate.getToolsEstimates().stream()
                .filter(x -> x.getName().equals(tool.getName()))
                .findAny().ifPresent(toolsEstimateRepo::delete);
    }
}
