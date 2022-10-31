package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wns.constants.CategoryTools;
import wns.entity.EstimateName;
import wns.entity.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstimateNameDTO {
    private long id;
    private String name;
    private String categoryTools;

    public EstimateNameDTO(EstimateName estimateName) {
        this.id = estimateName.getId();
        this.name = estimateName.getName();
        this.categoryTools = estimateName.getCategoryTools().getData();
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
