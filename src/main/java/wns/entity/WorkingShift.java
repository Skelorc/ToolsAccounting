package wns.entity;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import wns.constants.TypeShift;

import javax.persistence.*;
import java.time.LocalDate;

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
}
