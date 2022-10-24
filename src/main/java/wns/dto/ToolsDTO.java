package wns.dto;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import wns.constants.CategoryTools;
import wns.constants.StatusTools;
import wns.constants.TypeTools;
import wns.entity.EstimateName;
import wns.entity.Project;
import wns.entity.Status;
import wns.entity.Tools;

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
    private StatusTools status;
    private String status_string;
    private long id_status;
    private String project;
    private long id_project;
    private String estimateName;
    private long id_estimate_name;
    private String comment;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
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

    public ToolsDTO(Tools tools) {
        this.id = tools.getId();
        this.typeTools = tools.getTypeTools();
        this.name = tools.getName();
        this.barcode = tools.getBarcode();
        this.category = tools.getCategory();
        this.model = tools.getModel();
        this.serialNumber = tools.getSerialNumber();
        this.characteristics = tools.getCharacteristics();
        this.equip = tools.getEquip();
        this.amount = tools.getAmount();
        this.state = tools.getState();
        this.status = tools.getStatus().getStatusTools();
        this.id_status = tools.getStatus().getId();
        this.status_string = tools.getStatus().getStatusTools().getValue();
        if (tools.getProject() != null) {
            this.project = tools.getProject().getName();
            this.id_project = tools.getProject().getId();
        }
        else
        {
            this.project = "Нет проекта!";
        }
        this.estimateName = tools.getEstimateName().getName();
        this.id_estimate_name = tools.getEstimateName().getId();
        this.comment = tools.getComment();
        this.creating = tools.getCreating();
        this.costPrice = tools.getCostPrice();
        this.priceByDay = tools.getPriceByDay();
        this.incomeFromTools = tools.getIncomeFromTools();
        this.priceSell = tools.getPriceSell();
        this.incomeSales = tools.getIncomeSales();
        this.incomeInvestorProcents = tools.getIncomeInvestorProcents();
        this.incomeInvestor = tools.getIncomeInvestor();
        this.repairAmount = tools.getRepairAmount();
        this.numberWorkingShifts = tools.getNumberWorkingShifts();
        this.priceSublease = tools.getPriceSublease();
        this.paymentSublease = tools.getPaymentSublease();
        this.incomeAdditional = tools.getIncomeAdditional();
        this.photos.addAll(tools.getStatus().getPhotos());
    }

}


