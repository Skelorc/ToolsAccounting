package wns.entity;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import wns.constants.TypeShift;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "working_shift")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate dateShift;
    @Enumerated(EnumType.STRING)
    private TypeShift typeShift;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projects_id")
    private Project project;

    public WorkingShift(LocalDate dateShift, TypeShift typeShift) {
        this.dateShift = dateShift;
        this.typeShift = typeShift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingShift that = (WorkingShift) o;
        return Objects.equals(dateShift, that.dateShift) && typeShift == that.typeShift;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateShift, typeShift);
    }
}
