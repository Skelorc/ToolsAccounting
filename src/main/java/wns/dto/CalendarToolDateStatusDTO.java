package wns.dto;


import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CalendarToolDateStatusDTO {
    private LocalDateTime start;
    private LocalDateTime end;
    private String status;

}
