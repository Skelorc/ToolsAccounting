package wns.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import wns.constants.StatusTools;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "status_tools")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "status")
    private Tools tools;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_status")
    private StatusTools statusTools;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created;
    private String employee;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
    private String executor;
    private String phone_number;
    private String note;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "photos_status_tools", joinColumns = @JoinColumn(name = "status_tools_id"))
    @Column(columnDefinition = "TEXT")
    private Set<String> photos = new HashSet<>();
    private int priceRepair;
    private int priceSell;
    private int priceOff;
}
