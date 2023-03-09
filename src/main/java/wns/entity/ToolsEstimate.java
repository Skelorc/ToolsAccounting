package wns.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wns.constants.EstimateSection;

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
    private String owner;
    private String name;
    private String barcode;
    private String category;
    private String model;
    @Enumerated(EnumType.STRING)
    private EstimateSection section;
    private int amount;
    @Column(name = "price_by_day")
    private int priceByDay;
    private LocalDate creating;
    @Column(name = "count_shifts")
    private int countShifts;
    private int discount;

    private long totalByDay;
    private long totalByDayWithDiscount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_id",nullable = false)
    private Estimate estimate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToolsEstimate that = (ToolsEstimate) o;
        return id == that.id && amount == that.amount && priceByDay == that.priceByDay && countShifts == that.countShifts && discount == that.discount && Objects.equals(name, that.name) && Objects.equals(barcode, that.barcode) && category == that.category && Objects.equals(model, that.model) && section == that.section && Objects.equals(creating, that.creating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, barcode, category, model, section, amount, priceByDay, creating, countShifts, discount);
    }

    @Override
    public String toString() {
        return "ToolsEstimate{" +
                "id=" + id +
                ", typeTools=" + owner +
                ", name='" + name + '\'' +
                ", barcode='" + barcode + '\'' +
                ", category=" + category +
                ", model='" + model + '\'' +
                ", section=" + section +
                ", amount=" + amount +
                ", priceByDay=" + priceByDay +
                ", creating=" + creating +
                ", count_shifts=" + countShifts +
                ", discount=" + discount +
                '}';
    }
}
