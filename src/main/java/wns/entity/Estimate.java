package wns.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "estimate")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
    private int count_shifts;
    private String operator;
    @Column(name = "all_by_project")
    private long allByProject;
    @Column(name = "discount_by_tools")
    private int discountByTools;
    @Column(name = "all_by_project_with_discount")
    private long allByProjectWithDiscount;
    @Column(name = "all_by_service")
    private long allByService;
    @Column (name = "final_sum_by_project")
    private long finalSumByProject;
    @Column(name = "procent_usn")
    private int procentUsn;
    @Column(name = "final_sum_with_usn")
    private long finalSumWithUsn;


    @OneToOne(mappedBy = "estimate")
    private Project project;




    @OneToMany(mappedBy = "estimate", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ToolsEstimate> toolsEstimates = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estimate)) return false;
        Estimate estimate = (Estimate) o;
        return getId() == estimate.getId() && getCount_shifts() == estimate.getCount_shifts() && Objects.equals(getStart(), estimate.getStart()) && Objects.equals(getEnd(), estimate.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStart(), getEnd(), getCount_shifts());
    }

    @Override
    public String toString() {
        return "Estimate{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", count_shifts=" + count_shifts +
                ", allByPRoject=" + allByProject +
                ", discountByTools=" + discountByTools +
                ", allByProjectWithDiscount=" + allByProjectWithDiscount +
                ", allByService=" + allByService +
                ", finalSumByProject=" + finalSumByProject +
                ", procentUsn=" + procentUsn +
                ", finalSumWithUsn=" + finalSumWithUsn +
                '}';
    }
}
