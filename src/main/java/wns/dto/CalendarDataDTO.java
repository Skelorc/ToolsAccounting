package wns.dto;

import lombok.Data;
import wns.entity.Tools;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CalendarDataDTO {
    private String name;
    private LocalDateTime created;
    private long id;
    private List<CalendarDateStatusDTO> listStatusWithDate;

    public CalendarDataDTO(String name,long id,LocalDateTime created) {
        this.name = name;
        this.created = created;
        this.id = id;
        listStatusWithDate = new ArrayList<>();
    }

    public void addDataToList(LocalDateTime start, LocalDateTime end, String status)
    {
        CalendarDateStatusDTO calendarDateStatusDTO = new CalendarDateStatusDTO();
        calendarDateStatusDTO.setStart(start);
        calendarDateStatusDTO.setEnd(end);
        calendarDateStatusDTO.setStatus(status);
        listStatusWithDate.add(calendarDateStatusDTO);
    }
}
