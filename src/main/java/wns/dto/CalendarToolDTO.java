package wns.dto;

/*
 *@author Skelorc
 */

import lombok.Data;
import wns.entity.Tools;

import java.time.LocalDateTime;

@Data
public class CalendarToolDTO {
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String statusTools;

    public CalendarToolDTO(Tools tools) {
        this.name = tools.getName();
        this.start = tools.getStatus().getStart();
        this.end = tools.getStatus().getEnd();
        this.statusTools = tools.getStatus().getStatusTools().toString();
    }
}
