package wns.dto;

import lombok.Data;
import wns.entity.Category;

@Data
public class CategoryDTO {
    private long id;
    private String name;
    private String code;
    private long numberTool;

    public CategoryDTO(Category category) {
        this.id= category.getId();
        this.name= category.getName();
        this.code = category.getName();
        this.numberTool = category.getNumberTool();
    }
}
