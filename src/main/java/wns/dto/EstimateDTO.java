package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import wns.entity.Estimate;
import wns.entity.Project;
import wns.entity.ToolsEstimate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstimateDTO {
    private long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;
    private int countShifts;
    private String operator;
    private Project project;
    private List<ToolsEstimate> toolsEstimates = new ArrayList<>();
    private EstimateFieldsDTO params;

    public Estimate createEstimateFromProject(Project project)
    {
        Estimate estimate = project.getEstimate();
        estimate.setAllByProject(params.getAllByPRoject());
        estimate.setDiscountByTools(params.getDiscountByTools());
        estimate.setAllByProjectWithDiscount(params.getAllByProjectWithDiscount());
        estimate.setAllByService(params.getAllByService());
        estimate.setFinalSumByProject(params.getFinalSumByProject());
        estimate.setProcentUsn(params.getProcentUsn());
        estimate.setFinalSumWithUsn(params.getFinalSumWithUsn());
        estimate.setToolsEstimates(toolsEstimates);
        return estimate;
    }
}
