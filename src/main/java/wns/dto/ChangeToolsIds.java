package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeToolsIds {
    private List<Long> ids;
    private List<Long> old_ids;
    private List<Long> new_ids;
}
