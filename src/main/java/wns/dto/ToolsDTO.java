package wns.dto;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import wns.constants.EstimateSection;
import wns.constants.StatusTools;
import wns.entity.Tools;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ToolsDTO {
    private long id;
    private String owner;
    private long ownerId;
    private String name;
    private String barcode;
    private String category;
    private String model;
    private String serialNumber;
    private String characteristics;
    private String equip;
    private int amount;
    private String state;
    private EstimateSection section;
    private StatusTools status;
    private String status_string;
    private long id_status;
    private String project;
    private long id_project;
    private String estimateName;
    private String categoryToolsFromEstimate;
    private long categoryId;
    private long estimateNameId;
    private String comment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    private String photos;

    public ToolsDTO(Tools tools) {
        this.id = tools.getId();
        this.owner = tools.getOwner().getName();
        this.ownerId = tools.getOwner().getId();
        this.name = tools.getName();
        this.barcode = tools.getBarcode();
        this.category = tools.getCategory().getName();
        this.categoryId = tools.getCategory().getId();
        this.model = tools.getModel();
        this.serialNumber = tools.getSerialNumber();
        this.characteristics = tools.getCharacteristics();
        this.equip = tools.getEquip();
        this.amount = tools.getAmount();
        this.state = tools.getState();
        this.section = tools.getSection();
        this.status = tools.getStatus().getStatusTools();
        this.id_status = tools.getStatus().getId();
        this.status_string = tools.getStatus().getStatusTools().getValue();
        if (tools.getProject() != null) {
            this.project = tools.getProject().getName();
            this.id_project = tools.getProject().getId();
        } else {
            this.project = "Нет проекта!";
        }
        this.estimateName = tools.getEstimateName().getName();
        this.categoryToolsFromEstimate = tools.getEstimateName().getCategory().getData();
        this.estimateNameId = tools.getEstimateName().getId();
        if (tools.getComment() != null)
            this.comment = tools.getComment().getText();
        else
            comment = "";
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
        this.photos = tools.getPhotos().stream().collect(Collectors.joining(","));
    }

    public Tools FromDTOToTools(Tools tools) {
        tools.setSection(section);
        tools.setAmount(amount);
        tools.setBarcode(barcode);
        tools.setCharacteristics(characteristics);
        tools.setPhotos(new HashSet<>(Arrays.asList(photos.split(","))));
        tools.setName(name);
        tools.setModel(model);
        tools.setSerialNumber(serialNumber);
        tools.setEquip(equip);
        tools.setState(state);
        tools.setCreating(creating);
        tools.setCostPrice(costPrice);
        tools.setPriceByDay(priceByDay);
        tools.setIncomeFromTools(incomeFromTools);
        tools.setPriceSell(priceSell);
        tools.setIncomeSales(incomeSales);
        tools.setIncomeInvestorProcents(incomeInvestorProcents);
        tools.setIncomeInvestor(incomeInvestor);
        tools.setRepairAmount(repairAmount);
        tools.setNumberWorkingShifts(numberWorkingShifts);
        tools.setPriceSublease(priceSublease);
        tools.setPaymentSublease(paymentSublease);
        tools.setIncomeAdditional(incomeAdditional);
        tools.setPaymentSublease(paymentSublease);
        return tools;
    }


}


