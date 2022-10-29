package wns.dto;

import lombok.Data;
import wns.constants.CategoryTools;
import wns.entity.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class EstimateNameDTO {
    private long id;
    private String name;
    private CategoryTools categoryTools;
    private List<Tools> listTools = new ArrayList<>();

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
