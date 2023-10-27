package wns.dto;

/*
 *@author Skelorc
 */

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FiltersByCalendarDTO {
    private List<String> names = new ArrayList<>();
    private List<String> clients = new ArrayList<>();
    private List<String> workers = new ArrayList<>();
    private List<String> managers = new ArrayList<>();
    private List<String> types = new ArrayList<>();
}
