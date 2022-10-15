package wns.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import wns.dto.ClientDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PageableService {

    public <T> PageImpl<T>  findPaginated(Optional<Integer> page, Optional<Integer> size,
                                          List<T> list) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        int startItem = currentPage * pageSize;
        List<T> page_list;
        if (list.size() < startItem) {
            page_list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, list.size());
            page_list = list.subList(startItem, toIndex);
        }
        return new PageImpl<T>(page_list, PageRequest.of(currentPage, pageSize), list.size());
    }

    public <T> void addPageNumbersToModel(Page<T> list, Model model)
    {
        int totalPages = list.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
    }
}
