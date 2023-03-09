package wns.dto;

import lombok.*;
import wns.entity.EstimateName;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstimateNameDTO {
    private long id;
    private String name;
    private String categoryTools;
    private long categoryId;

    public EstimateNameDTO(EstimateName estimateName) {
        this.id = estimateName.getId();
        this.name = estimateName.getName();
        this.categoryTools = estimateName.getCategory().getName();
        this.categoryId = estimateName.getCategory().getId();
    }

    public EstimateName createEstimateNameDTO()
    {
        EstimateName estimateName = new EstimateName();
        estimateName.setId(id);
        estimateName.setName(name);
        return estimateName;
    }

    @Override
    public String toString() {
        return "EstimateNameDTO{" +
                "id=" + id +
                ", nameEstimate='" + name + '\'' +
                ", categoryTools=" + categoryTools +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstimateNameDTO that = (EstimateNameDTO) o;
        return id == that.id && Objects.equals(name, that.name) && categoryTools == that.categoryTools;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, categoryTools);
    }
}
