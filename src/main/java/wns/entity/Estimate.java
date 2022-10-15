package wns.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;
    private int count_shifts;

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
}
