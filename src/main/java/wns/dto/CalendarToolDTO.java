package wns.dto;

import lombok.Data;
import wns.entity.Tools;

import java.util.ArrayList;
import java.util.List;

@Data
public class CalendarToolDTO {
    private String name;
    private List<CalendarToolDateStatusDTO> listStatusWithDate;

    public CalendarToolDTO(Tools tools) {
        this.name = tools.getName();
        listStatusWithDate = new ArrayList<>();
    }

    public void addDataToList(Tools tools)
    {
        CalendarToolDateStatusDTO calendarToolDateStatusDTO = new CalendarToolDateStatusDTO();
        calendarToolDateStatusDTO.setStart(tools.getStatus().getStart());
        calendarToolDateStatusDTO.setEnd(tools.getStatus().getEnd());
        calendarToolDateStatusDTO.setStatus(tools.getStatus().getStatusTools().toString());
        listStatusWithDate.add(calendarToolDateStatusDTO);
    }
}
