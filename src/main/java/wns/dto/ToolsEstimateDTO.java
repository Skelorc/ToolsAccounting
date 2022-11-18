package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wns.constants.CategoryTools;
import wns.constants.EstimateSection;
import wns.constants.TypeTools;
import wns.entity.Category;
import wns.entity.Estimate;
import wns.entity.Owner;
import wns.entity.Tools;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolsEstimateDTO {
    private long id;
    private Owner typeTools;
    private String name;
    private String barcode;
    private Category category;
    private String model;
    private EstimateSection section;
    private int amount;
    private int priceByDay;
    private LocalDate creating;
    private int count_shifts;
    private int discount;
    private Estimate estimate;

    public ToolsEstimateDTO(Tools tools, Estimate estimate) {
        this.id = tools.getId();
        this.typeTools = tools.getOwner();
        this.name = tools.getName();
        this.barcode = tools.getBarcode();
        this.category = tools.getCategory();
        this.model = tools.getModel();
        this.section = tools.getSection();
        this.amount = tools.getAmount();
        this.priceByDay = tools.getPriceByDay();
        this.creating = tools.getCreating();
        this.count_shifts = estimate.getProject().getWorkingShifts().size();
        this.estimate = estimate;
    }

}
