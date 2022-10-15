package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wns.constants.CategoryTools;
import wns.constants.EstimateSection;
import wns.constants.TypeTools;
import wns.entity.Estimate;
import wns.entity.Tools;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolsEstimateDTO {
    private long id;
    private TypeTools typeTools;
    private String name;
    private String barcode;
    private CategoryTools category;
    private String model;
    private String serialNumber;
    private String characteristics;
    private int amount;
    private EstimateSection section;
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
    private Estimate estimate;

    public ToolsEstimateDTO(Tools tools, Estimate estimate) {
        this.typeTools = tools.getTypeTools();
        this.name = tools.getName();
        this.barcode = tools.getBarcode();
        this.category = tools.getCategory();
        this.model = tools.getModel();
        this.serialNumber = tools.getSerialNumber();
        this.characteristics = tools.getCharacteristics();
        this.section = tools.getSection();
        this.amount = tools.getAmount();
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
        this.estimate = estimate;
    }

}
