package wns.dto;

/*
 *@author Skelorc
 */

import lombok.Data;
import lombok.RequiredArgsConstructor;
import wns.constants.Filter;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class CalendarFilterDTO {
    private Filter filter;
    private LocalDate dateStart;
}
