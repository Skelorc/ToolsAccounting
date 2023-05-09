package wns.dto;

import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import wns.constants.Filter;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class PageDataDTO implements Pageable {

    private Optional<Integer> page;
    private Optional<Integer> size;
    private long categoryId;
    private String section;
    private Filter filter;
    private long id = -1;
    private LocalDate startDateForCalendar;

    public PageDataDTO(Optional<Integer> page, Optional<Integer> size, Filter filter) {
        this.page = page;
        this.size = size;
        this.filter = filter;
    }

    public PageDataDTO(Optional<Integer> page, Optional<Integer> size, Filter filter, LocalDate startDateForCalendar) {
        this.page = page;
        this.size = size;
        this.filter = filter;
        this.startDateForCalendar = startDateForCalendar;
    }

    public PageDataDTO(Optional<Integer> page, Optional<Integer> size, Filter filter, long id) {
        this.page = page;
        this.size = size;
        this.filter = filter;
        this.id = id;
    }

    public PageDataDTO(Optional<Integer> page, Optional<Integer> size, Filter filter, long categoryId, String section) {
        this.page = page;
        this.size = size;
        this.section = section;
        this.categoryId = categoryId;
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

    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
