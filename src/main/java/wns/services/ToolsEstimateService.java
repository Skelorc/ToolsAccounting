package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.entity.*;
import wns.repo.ToolsEstimateRepo;

import java.time.Period;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ToolsEstimateService {
    private final ToolsEstimateRepo toolsEstimateRepo;

    @Transactional(readOnly = true)
    public List<ToolsEstimate> getAll() {
        return (List<ToolsEstimate>) toolsEstimateRepo.findAll();
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
        ToolsEstimate toolsEstimate = new ToolsEstimate();
        toolsEstimate.setEstimate(estimate);
        toolsEstimate.setOwner(tool.getOwner().getName());
        toolsEstimate.setName(tool.getName());
        toolsEstimate.setBarcode(tool.getBarcode());
        toolsEstimate.setCategory(tool.getCategory().getName());
        toolsEstimate.setModel(tool.getModel());
        toolsEstimate.setSection(tool.getSection());
        toolsEstimate.setAmount(tool.getAmount());
        toolsEstimate.setPriceByDay(tool.getPriceByDay());
        toolsEstimate.setCreating(tool.getCreating());
        toolsEstimate.setCountShifts(project.getWorkingShifts().size());
        toolsEstimateRepo.save(toolsEstimate);
    }

    public void deleteToolEstimateFromEstimate(Tools tool) {
        Project project = tool.getProject();
        Estimate estimate = project.getEstimate();
        estimate.getToolsEstimates().stream()
                .filter(x -> x.getName().equals(tool.getName()))
                .findAny().ifPresent(toolsEstimateRepo::delete);
    }

    public void delete(long id) {
        toolsEstimateRepo.deleteById(id);
    }
}
