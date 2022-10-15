package wns.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wns.constants.CategoryTools;
import wns.constants.EstimateSection;
import wns.constants.TypeTools;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tools_estimate")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ToolsEstimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private TypeTools typeTools;
    private String name;
    private String barcode;

    @Enumerated(EnumType.STRING)
    private CategoryTools category;
    private String model;
    @Column(name = "serial_number")
    private String serialNumber;

    @Column(columnDefinition = "TEXT")
    private String characteristics;

    @Enumerated(EnumType.STRING)
    private EstimateSection section;
    private int amount;
    @Column(columnDefinition = "TEXT")
    private String comment;
    private LocalDate creating;
    @Column(name = "cost_price")
    private long costPrice;
    @Column(name = "price_by_day")
    private int priceByDay;
    @Column(name = "income_from_tools")
    private int incomeFromTools;
    @Column(name = "price_sell")
    private int priceSell;
    @Column(name = "income_sales")
    private int incomeSales;
    @Column(name = "income_investor_procents")
    private int incomeInvestorProcents;
    @Column(name = "income_investor")
    private int incomeInvestor;
    @Column(name = "repair_amount")
    private int repairAmount;
    @Column(name = "number_working_shifts")
    private int numberWorkingShifts;
    @Column(name = "price_sublease")
    private int priceSublease;
    @Column(name = "payment_sublease")
    private int paymentSublease;
    @Column(name = "income_additional")
    private int incomeAdditional;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_id",nullable = false)
    private Estimate estimate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToolsEstimate)) return false;
        ToolsEstimate that = (ToolsEstimate) o;
        return getId() == that.getId() && getAmount() == that.getAmount() && getCostPrice() == that.getCostPrice() && getPriceByDay() == that.getPriceByDay() && getIncomeFromTools() == that.getIncomeFromTools() && getPriceSell() == that.getPriceSell() && getIncomeSales() == that.getIncomeSales() && getIncomeInvestorProcents() == that.getIncomeInvestorProcents() && getIncomeInvestor() == that.getIncomeInvestor() && getRepairAmount() == that.getRepairAmount() && getNumberWorkingShifts() == that.getNumberWorkingShifts() && getPriceSublease() == that.getPriceSublease() && getPaymentSublease() == that.getPaymentSublease() && getIncomeAdditional() == that.getIncomeAdditional() && getTypeTools() == that.getTypeTools() && Objects.equals(getName(), that.getName()) && Objects.equals(getBarcode(), that.getBarcode()) && getCategory() == that.getCategory() && Objects.equals(getModel(), that.getModel()) && Objects.equals(getSerialNumber(), that.getSerialNumber()) && Objects.equals(getCharacteristics(), that.getCharacteristics()) && getSection() == that.getSection() && Objects.equals(getComment(), that.getComment()) && Objects.equals(getCreating(), that.getCreating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTypeTools(), getName(), getBarcode(), getCategory(), getModel(), getSerialNumber(), getCharacteristics(), getSection(), getAmount(), getComment(), getCreating(), getCostPrice(), getPriceByDay(), getIncomeFromTools(), getPriceSell(), getIncomeSales(), getIncomeInvestorProcents(), getIncomeInvestor(), getRepairAmount(), getNumberWorkingShifts(), getPriceSublease(), getPaymentSublease(), getIncomeAdditional());
    }
}
