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
    private long resultByToolsInShift;
    private int discountByTools;
    private long resultByToolsWithDiscount;
    private long totalByTools;
    private long resultByServiceInShift;
    private long totalByService;
    private long totalByProject;
    private int procentUsn;
    private long finalTotalWithUsn;

    public Estimate setDataToEstimateFromDTO(Estimate estimate) {
        estimate.setOperator(operator);
        estimate.setResultByToolsInShift(resultByToolsInShift);
        estimate.setDiscountByTools(discountByTools);
        estimate.setResultByToolsWithDiscount(resultByToolsWithDiscount);
        estimate.setTotalByTools(totalByTools);
        estimate.setResultByServiceInShift(resultByServiceInShift);
        estimate.setTotalByService(totalByService);
        estimate.setTotalByProject(totalByProject);
        estimate.setProcentUsn(procentUsn);
        estimate.setFinalTotalWithUsn(finalTotalWithUsn);
        estimate.setToolsEstimates(toolsEstimates);
        return estimate;
    }
}
