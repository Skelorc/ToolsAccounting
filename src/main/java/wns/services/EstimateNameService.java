package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import wns.dto.EstimateNameDTO;
import wns.entity.EstimateName;
import wns.entity.Owner;
import wns.entity.Tools;
import wns.repo.EstimateNameRepo;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class EstimateNameService implements MainService {
    private final EstimateNameRepo repo;
    private final ModelMapper modelMapper;

    public List<EstimateNameDTO> getAll() {
        return repo.findAll().stream()
                .map(EstimateNameDTO::new)
                .collect(Collectors.toList());
    }

    public void save(EstimateName dto) {
        EstimateName byName = repo.findByName(dto.getName());
        if (byName == null) {
            EstimateName estimateName = modelMapper.map(dto, EstimateName.class);
            repo.save(estimateName);
        }
    }

    public void save(Tools tools_to_save, long id) {
        EstimateName estimateName = repo.getById(id);
        estimateName.getListTools().add(tools_to_save);
        repo.save(estimateName);
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }

    public EstimateName getNameEstimateById(long id) {
        return repo.findById(id).orElse(new EstimateName());
    }

}
