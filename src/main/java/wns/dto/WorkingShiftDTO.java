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

    public WorkingShift creteWorkingShiftFromDTO(WorkingShiftDTO dto)
    {
        WorkingShift workingShift = new WorkingShift();
        workingShift.setDateShift(dto.getDateShift());
        workingShift.setTypeShift(dto.getTypeShift());
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
