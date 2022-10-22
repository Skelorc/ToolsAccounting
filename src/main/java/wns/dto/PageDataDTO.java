package wns.dto;

import lombok.*;
import wns.constants.Filter;
import wns.constants.PaginationConst;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class PageDataDTO {
    private Optional<Integer> page;
    private Optional<Integer> size;
    private Filter filter;
    private PaginationConst paginationConst;
    private long id_object;
}
