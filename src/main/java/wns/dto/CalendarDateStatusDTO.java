package wns.dto;


import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CalendarDateStatusDTO {
    private LocalDateTime start;
    private LocalDateTime end;
    private String status;

}
