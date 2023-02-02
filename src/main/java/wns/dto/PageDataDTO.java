package wns.dto;

import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import wns.constants.CategoryTools;
import wns.constants.EstimateSection;
import wns.constants.Filter;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class PageDataDTO implements Pageable {

    private Optional<Integer> page;
    private Optional<Integer> size;
    private CategoryTools categoryTools;
    private EstimateSection section;
    private Filter filter;
    private long id = -1;

    public PageDataDTO(Optional<Integer> page, Optional<Integer> size, Filter filter) {
        this.page = page;
        this.size = size;
        this.filter = filter;
    }

    public PageDataDTO(Optional<Integer> page, Optional<Integer> size, Filter filter, long id) {
        this.page = page;
        this.size = size;
        this.filter = filter;
        this.id = id;
    }

    public PageDataDTO(Optional<Integer> page, Optional<Integer> size, Filter filter, CategoryTools categoryTools, EstimateSection section) {
        this.page = page;
        this.size = size;
        this.section = section;
        this.categoryTools = categoryTools;
        this.filter = filter;
    }

    @Override
    public int getPageNumber() {
        return page.get();
    }

    @Override
    public int getPageSize() {
        return size.get();
    }

    @Override
    public long getOffset() {
        return (long)this.page.get() * (long)this.size.get();
    }

    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
