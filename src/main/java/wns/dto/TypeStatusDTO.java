package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import wns.constants.StatusTools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TypeStatusDTO {
    private String data;
    private String executor;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created;
    private String note;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;
    private Set<String> photos;
    private String phoneNumber;
    private StatusTools statusTools;
    private int price;
    private Map<Long, Identifiers> items;
}
