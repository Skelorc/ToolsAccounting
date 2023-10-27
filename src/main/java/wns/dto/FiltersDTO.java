package wns.dto;

/*
 *@author Skelorc
 */

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FiltersDTO {
    private String names;
    private String clients;
    private String managers;
    private String types;
    private String workers;
}
