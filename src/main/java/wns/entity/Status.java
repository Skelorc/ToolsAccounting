package wns.entity;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import wns.constants.StatusTools;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "status_tools")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "status")
    private Tools tools;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_status")
    private StatusTools statusTools;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created;
    private String employee;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;
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

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", statusTools=" + statusTools +
                ", created=" + created +
                ", employee='" + employee + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", executor='" + executor + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", note='" + note + '\'' +
                ", photos=" + photos +
                ", priceRepair=" + priceRepair +
                ", priceSell=" + priceSell +
                ", priceOff=" + priceOff +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Status status = (Status) o;

        return new EqualsBuilder().append(id, status.id).append(priceOff, status.priceOff).append(priceRepair, status.priceRepair).append(priceSell, status.priceSell).append(statusTools, status.statusTools).append(created, status.created).append(employee, status.employee).append(start, status.start).append(end, status.end).append(executor, status.executor).append(phone_number, status.phone_number).append(note, status.note).append(photos, status.photos).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(statusTools).append(created).append(employee).append(start).append(end).append(executor).append(phone_number).append(note).append(photos).append(priceRepair).append(priceSell).toHashCode();
    }
}
