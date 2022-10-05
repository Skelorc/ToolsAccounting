package wns.entity;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import wns.constants.CategoryTools;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

        return new EqualsBuilder().append(id, that.id).append(name, that.name).append(categoryTools, that.categoryTools).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).toHashCode();
    }
}
