package wns.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wns.constants.ClassificationProject;
import wns.constants.StatusProject;
import wns.constants.TypeLease;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name ="projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long number;
    @Enumerated(EnumType.STRING)
    private ClassificationProject classification;
    @Enumerated(EnumType.STRING)
    private StatusProject status;
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_lease")
    private TypeLease typeLease;
    private int quantity;
    @Column(name = "creating_date")
    private LocalDateTime creatingDate;
    private String employee;
    private LocalDateTime start;
    private LocalDateTime end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clients_id",nullable = false)
    private Client client;
    @Column(name = "phone_number")
    private String phoneNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "photos_projects", joinColumns = @JoinColumn(name = "projects_id"))
    private Set<String> photos;

    private int discount;
    @Column(columnDefinition = "TEXT")
    private String note;
    private long sum;
    @Column(name = "final_sum_usn")
    private long finalSumUsn;
    @Column(name = "price_tools")
    private int priceTools;
    @Column(name = "price_work")
    private long priceWork;
    @Column(name = "discount_by_project")
    private int discountByProject;
    @Column(name = "sum_with_discount")
    private long sumWithDiscount;
    private long received;
    private long remainder;

    //Мапинг по переменной, в данном случае это project
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Tools> tools = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id && number == project.number && quantity == project.quantity && discount == project.discount && sum == project.sum && finalSumUsn == project.finalSumUsn && priceTools == project.priceTools && priceWork == project.priceWork && discountByProject == project.discountByProject && sumWithDiscount == project.sumWithDiscount && received == project.received && remainder == project.remainder && classification == project.classification && status == project.status && Objects.equals(name, project.name) && typeLease == project.typeLease && Objects.equals(creatingDate, project.creatingDate) && Objects.equals(employee, project.employee) && Objects.equals(start, project.start) && Objects.equals(end, project.end) && Objects.equals(client, project.client) && Objects.equals(phoneNumber, project.phoneNumber) && Objects.equals(note, project.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, classification, status, name, typeLease, quantity, creatingDate, employee, start, end, client, phoneNumber, discount, note, sum, finalSumUsn, priceTools, priceWork, discountByProject, sumWithDiscount, received, remainder);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", number=" + number +
                ", classification=" + classification +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", typeLease=" + typeLease +
                ", quantity=" + quantity +
                ", creatingDate=" + creatingDate +
                ", employee='" + employee + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", client=" + client +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", discount=" + discount +
                ", note='" + note + '\'' +
                ", sum=" + sum +
                ", finalSumUsn=" + finalSumUsn +
                ", priceTools=" + priceTools +
                ", priceWork=" + priceWork +
                ", discountByProject=" + discountByProject +
                ", sumWithDiscount=" + sumWithDiscount +
                ", received=" + received +
                ", remainder=" + remainder +
                '}';
    }
}
