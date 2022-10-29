package wns.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wns.constants.CategoryTools;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "estimate_name")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EstimateName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private CategoryTools categoryTools;
    @OneToMany(mappedBy = "estimateName", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Tools> listTools = new ArrayList<>();

    @Override
    public String toString() {
        return "EstimateName{" +
                "id=" + id +
                ", nameEstimate='" + name + '\'' +
                ", categoryTools=" + categoryTools +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstimateName that = (EstimateName) o;
        return id == that.id && Objects.equals(name, that.name) && categoryTools == that.categoryTools;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, categoryTools);
    }
}
