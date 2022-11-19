package wns.dto;

import wns.constants.TypeShift;
import wns.entity.WorkingShift;

import java.time.LocalDate;

public class WorkingShiftDTO {
    private long id;

    private LocalDate dateShift;
    private TypeShift typeShift;
    private long project_id;

    public WorkingShiftDTO(WorkingShift workingShift) {
        this.id = workingShift.getId();
        this.dateShift = workingShift.getDateShift();
        this.typeShift = workingShift.getTypeShift();
        this.project_id = workingShift.getProject().getId();
    }
}
