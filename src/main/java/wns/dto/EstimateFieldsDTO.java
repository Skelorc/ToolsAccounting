package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstimateFieldsDTO {
    private long allByPRoject;
    private int discountByTools;
    private long allByProjectWithDiscount;
    private long allByService;
    private long finalSumByProject;
    private int procentUsn;
    private long finalSumWithUsn;
}
