package wns.entity;

import lombok.*;
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

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Tools> tools;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_status")
    private StatusTools statusTools;
    private LocalDate created;
    private String employee;
    private LocalDateTime start;
    private LocalDateTime end;
    private String executor;
    private String phone_number;
    private String note;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "photos_status_tools", joinColumns = @JoinColumn(name = "status_tools_id"))
    private Set<String> photos = new HashSet<>();
    private int price;
}
