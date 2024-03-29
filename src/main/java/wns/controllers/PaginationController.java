package wns.controllers;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wns.constants.Messages;
import wns.dto.PageDataDTO;
import wns.services.PageableFilterService;
import wns.utils.ResponseHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("pagination")
@AllArgsConstructor
public class PaginationController {

    private final PageableFilterService pageableFilterService;

    @PostMapping()
    @ResponseBody
    public ResponseEntity<Object> pagination(@RequestBody PageDataDTO pageDataDTO) {
        Page<Object> paginated_list = pageableFilterService.getListData(pageDataDTO);
        int totalPages = paginated_list.getTotalPages();
        List<Integer> pageNumbers = null;
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        Map<Integer, Object> data = new HashMap<>();
        data.put(1,paginated_list);
        data.put(2,pageNumbers);
        return ResponseHandler.generateResponse(Messages.OK,"",data);
    }
}
