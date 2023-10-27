package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.aspects.ToLog;
import wns.dto.EstimateNameDTO;
import wns.entity.Category;
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
    public void save(EstimateNameDTO dto, Category category) {
        EstimateName estimateName = dto.createEstimateNameDTO();
        estimateName.setCategory(category);
        estimateName.setId(dto.getId());
        repo.save(estimateName);
    }

    public void save(EstimateNameDTO dto) {
        EstimateName estimateName = dto.createEstimateNameDTO();
        estimateName.setId(dto.getId());
        repo.save(estimateName);
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
    public EstimateNameDTO getNameEstimateById(long id) {
        return new EstimateNameDTO(repo.findById(id).orElse(new EstimateName()));
    }

}
