package wns.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import wns.constants.CategoryTools;
import wns.entity.Tools;

import java.util.ArrayList;
import java.util.List;

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

        return new EqualsBuilder().append(id, that.id).append(name, that.name).append(categoryTools, that.categoryTools).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).toHashCode();
    }
}
