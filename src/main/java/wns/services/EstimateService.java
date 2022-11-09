package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wns.constants.EstimateSection;
import wns.entity.*;
import wns.repo.EstimateRepo;

import java.time.Period;
import java.util.*;

@Service
@AllArgsConstructor
public class EstimateService implements MainService{
    private final EstimateRepo estimateRepo;
    private ToolsEstimateService toolsEstimateService;
    @Override
    public List<Estimate> getAll() {
       return estimateRepo.findAll();
    }

    public void save(Estimate estimate)
    {
        estimate.getToolsEstimates().forEach(x -> x.setEstimate(estimate));
        estimate.getToolsEstimates().forEach(x -> toolsEstimateService.save(x));
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

    @Override
    public void delete(long id) {
        estimateRepo.deleteById(id);
    }

    public Estimate findById(long id) {
        return estimateRepo.findById(id).get();
    }
}
