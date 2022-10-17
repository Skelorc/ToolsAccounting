package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import wns.constants.EstimateSection;
import wns.dto.ToolsEstimateDTO;
import wns.entity.Estimate;
import wns.entity.Project;
import wns.entity.Tools;
import wns.entity.ToolsEstimate;
import wns.repo.EstimateRepo;

import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EstimateService implements MainService{
    private final EstimateRepo estimateRepo;
    private ToolsEstimateService toolsEstimateService;
    private ModelMapper modelMapper;
    @Override
    public  List<Estimate> getAll() {
       return estimateRepo.findAll();
    }

    public void save(Estimate estimate)
    {
        estimateRepo.save(estimate);
    }

    public Estimate createEstimate(Project project)
    {
        Estimate estimate = new Estimate();
        estimate.setProject(project);
        estimate.setStart(project.getStart());
        estimate.setEnd(project.getEnd());
        estimate.setCount_shifts(Period.between(estimate.getStart().toLocalDate(), estimate.getEnd().toLocalDate()).getDays());
        return estimate;
    }

    public Map<EstimateSection, List<ToolsEstimate>> getToolsEstimate(Estimate estimate) {
        List<ToolsEstimate> toolsEstimates = estimate.getToolsEstimates();
        Map<EstimateSection, List<ToolsEstimate>> tools_by_groups = new HashMap<>();
        for (ToolsEstimate tool : toolsEstimates) {
            EstimateSection section = tool.getSection();
            if (tools_by_groups.get(section) != null)
                tools_by_groups.get(section).add(tool);
            else {
                List<ToolsEstimate> list = new ArrayList<>();
                list.add(tool);
                tools_by_groups.put(section, list);
            }
        }
        return tools_by_groups;
    }

    public void delete(Estimate estimate) {
        estimateRepo.delete(estimate);
    }
}