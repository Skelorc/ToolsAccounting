package wns.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wns.constants.CategoryTools;
import wns.constants.StatusTools;
import wns.constants.TypeTools;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tools")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tools{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "type_tools")
    @Enumerated(EnumType.STRING)
    private TypeTools typeTools;
    private String name;
    @Column(name = "name_estimate")
    private String nameEstimate;
    private String barcode;

    @Enumerated(EnumType.STRING)
    private CategoryTools category;
    private String model;
    @Column(name = "serial_number")
    private String serialNumber;

    @Column(columnDefinition = "TEXT")
    private String characteristics;

    private String equip;
    private int amount;
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_tools_id",nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projects_id")
    private Project project;

    @Column(columnDefinition = "TEXT")
    private String comment;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "photos_tools", joinColumns = @JoinColumn(name = "tools_id"))
    private Set<String> photos = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tools tools = (Tools) o;
        return id == tools.id && amount == tools.amount && costPrice == tools.costPrice && priceByDay == tools.priceByDay && incomeFromTools == tools.incomeFromTools && priceSell == tools.priceSell && incomeSales == tools.incomeSales && incomeInvestorProcents == tools.incomeInvestorProcents && incomeInvestor == tools.incomeInvestor && repairAmount == tools.repairAmount && numberWorkingShifts == tools.numberWorkingShifts && priceSublease == tools.priceSublease && paymentSublease == tools.paymentSublease && incomeAdditional == tools.incomeAdditional && typeTools == tools.typeTools && Objects.equals(name, tools.name) && Objects.equals(nameEstimate, tools.nameEstimate) && Objects.equals(barcode, tools.barcode) && category == tools.category && Objects.equals(model, tools.model) && Objects.equals(serialNumber, tools.serialNumber) && Objects.equals(characteristics, tools.characteristics) && Objects.equals(equip, tools.equip) && Objects.equals(state, tools.state) && status == tools.status && Objects.equals(project, tools.project) && Objects.equals(comment, tools.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeTools, name, nameEstimate, barcode, category, model, serialNumber, characteristics, equip, amount, state, status, project, comment, costPrice, priceByDay, incomeFromTools, priceSell, incomeSales, incomeInvestorProcents, incomeInvestor, repairAmount, numberWorkingShifts, priceSublease, paymentSublease, incomeAdditional);
    }

    @Override
    public String toString() {
        return "Tools{" +
                "id=" + id +
                ", typeTools=" + typeTools +
                ", name='" + name + '\'' +
                ", nameEstimate='" + nameEstimate + '\'' +
                ", barcode='" + barcode + '\'' +
                ", category=" + category +
                ", model='" + model + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", characteristics='" + characteristics + '\'' +
                ", set='" + equip + '\'' +
                ", amount=" + amount +
                ", state='" + state + '\'' +
                ", status=" + status +
                ", project=" + project +
                ", comment='" + comment + '\'' +
                ", costPrice=" + costPrice +
                ", priceByDay=" + priceByDay +
                ", incomeFromTools=" + incomeFromTools +
                ", priceSell=" + priceSell +
                ", incomeSales=" + incomeSales +
                ", incomeInvestorProcents=" + incomeInvestorProcents +
                ", incomeInvestor=" + incomeInvestor +
                ", repairAmount=" + repairAmount +
                ", numberWorkingShifts=" + numberWorkingShifts +
                ", priceSublease=" + priceSublease +
                ", paymentSublease=" + paymentSublease +
                ", incomeAdditional=" + incomeAdditional +
                '}';
    }
}
