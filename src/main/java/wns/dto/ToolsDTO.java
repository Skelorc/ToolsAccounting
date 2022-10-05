package wns.dto;


import lombok.*;
import wns.constants.CategoryTools;
import wns.constants.TypeTools;
import wns.entity.Project;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ToolsDTO {
    private long id;
    private TypeTools typeTools;
    private String name;
    private String barcode;
    private CategoryTools category;
    private String model;
    private String serialNumber;
    private String characteristics;
    private String equip;
    private int amount;
    private String state;
    private StatusToolDTO status;
    private Project project;
    private EstimateNameDTO estimateNameDTO;
    private String comment;
    private LocalDate creating;
    private long costPrice;
    private int priceByDay;
    private int incomeFromTools;
    private int priceSell;
    private int incomeSales;
    private int incomeInvestorProcents;
    private int incomeInvestor;
    private int repairAmount;
    private int numberWorkingShifts;
    private int priceSublease;
    private int paymentSublease;
    private int incomeAdditional;
    private Set<String> photos = new HashSet<>();

}
