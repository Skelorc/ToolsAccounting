package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.aspects.ToLog;
import wns.dto.EstimateNameDTO;
import wns.entity.EstimateName;
import wns.entity.Owner;
import wns.entity.Tools;
import wns.repo.EstimateNameRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
@Transactional
public class EstimateNameService {
    private final EstimateNameRepo repo;

    @Transactional(readOnly = true)
    public List<EstimateNameDTO> getAll() {
        List<EstimateName> all = (List<EstimateName>) repo.findAll();
        return all.stream()
                .map(EstimateNameDTO::new)
                .collect(Collectors.toList());
    }

    @ToLog
    public void save(EstimateName dto) {
        if (!repo.existsById(dto.getId())) {
            repo.save(dto);
        } else {
            EstimateName estimateName = repo.findById(dto.getId()).get();
            estimateName.setName(dto.getName());
            estimateName.setCategoryTools(dto.getCategoryTools());
            estimateName.setListTools(dto.getListTools());
            repo.save(estimateName);
        }
    }

    public void save(Tools tools_to_save, long id) {
        EstimateName estimateName = repo.findById(id).get();
        estimateName.getListTools().add(tools_to_save);
        repo.save(estimateName);
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public EstimateName getNameEstimateById(long id) {
        return repo.findById(id).orElse(new EstimateName());
    }

}
