package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstimateFieldsDTO {
    private long resultByToolsInShift;
    private int discountByTools;
    private long resultByToolsWithDiscount;
    private long totalByTools;
    private long resultByServiceInShift;
    private long totalByService;
    private long totalByProject;
    private int procentUsn;
    private long finalTotalWithUsn;
}
