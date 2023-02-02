package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.aspects.ToLog;
import wns.constants.EstimateSection;
import wns.dto.ProjectDTO;
import wns.entity.*;
import wns.repo.EstimateRepo;

import java.time.Period;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class EstimateService {
    private final EstimateRepo estimateRepo;
    private final ToolsEstimateService toolsEstimateService;

    @Transactional(readOnly = true)
    public List<Estimate> getAll() {
       return (List<Estimate>) estimateRepo.findAll();
    }

    public void save(Estimate estimate)
    {
        estimate.getToolsEstimates().forEach(x -> x.setEstimate(estimate));
        estimate.getToolsEstimates().forEach(toolsEstimateService::save);
        estimateRepo.save(estimate);
    }

    @Transactional(readOnly = true)
    @ToLog
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

    public void delete(long id) {
        estimateRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Estimate findById(long id) {
        return estimateRepo.findById(id).get();
    }
}
