package wns.dto;

/*
 *@author Skelorc
 */

import lombok.Data;
import lombok.RequiredArgsConstructor;
import wns.constants.Filter;

import java.time.LocalDate;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class CalendarFilterDTO {
    private Filter filter;
    private String value;
    private LocalDate dateStart;
    private FiltersDTO filters;
}
