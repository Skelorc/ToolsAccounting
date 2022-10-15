package wns.entity;

import lombok.*;
import wns.constants.CategoryTools;
import wns.constants.EstimateSection;
import wns.constants.TypeTools;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tools")
@Data
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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "status_tools_id",referencedColumnName = "id",nullable = false)
    private Status status;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projects_id")
    private Project project;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_name_id")
    private EstimateName estimateName;
    @Enumerated(EnumType.STRING)
    private EstimateSection section;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "photos_tools", joinColumns = @JoinColumn(name = "tools_id"))
    @Column(columnDefinition = "TEXT")
    private Set<String> photos = new HashSet<>();

}
