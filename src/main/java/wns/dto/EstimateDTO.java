package wns.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import wns.entity.Project;
import wns.entity.ToolsEstimate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class EstimateDTO {
    private long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;
    private int count_shifts;
    private Project project;
    private List<ToolsEstimate> toolsEstimates = new ArrayList<>();
}
