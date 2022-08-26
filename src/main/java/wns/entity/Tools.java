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

    private String set;
    private int amount;
    private String state;

    @Enumerated(EnumType.STRING)
    private StatusTools status;

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

}
