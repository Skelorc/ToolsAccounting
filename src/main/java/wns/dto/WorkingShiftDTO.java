package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import wns.constants.TypeShift;
import wns.entity.WorkingShift;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkingShiftDTO {
    private long id;
    private LocalDate dateShift;
    private TypeShift typeShift;
    private long project_id;

    public WorkingShift creteWorkingShiftFromDTO()
    {
        WorkingShift workingShift = new WorkingShift();
        workingShift.setDateShift(dateShift);
        workingShift.setTypeShift(typeShift);
        return workingShift;
    }

    public WorkingShiftDTO(WorkingShift workingShift)
    {
        this.id = workingShift.getId();
        this.dateShift = workingShift.getDateShift();
        this.typeShift = workingShift.getTypeShift();
        this.project_id = workingShift.getProject().getId();
    }

}
