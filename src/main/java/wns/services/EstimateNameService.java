package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wns.constants.Messages;
import wns.dto.EstimateNameDTO;
import wns.dto.ToolsDTO;
import wns.entity.EstimateName;
import wns.entity.Tools;
import wns.repo.EstimateNameRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class EstimateNameService implements MainService {
    private final EstimateNameRepo repo;
    private final ModelMapper modelMapper;

    public List<EstimateNameDTO> getAll()
    {
        return repo.findAll().stream()
                .map(x -> modelMapper.map(x, EstimateNameDTO.class))
                .collect(Collectors.toList());
    }
    public Messages save(EstimateNameDTO dto)
    {
        EstimateName byName = repo.findByName(dto.getName());
        if(byName==null)
        {
            EstimateName estimateName = modelMapper.map(dto, EstimateName.class);
            repo.save(estimateName);
            return Messages.NAME_ESTIMATE_CREATE;
        }
        else
            return Messages.NAME_ESTIMATE_EXISTS;
    }

    public void save(EstimateName estimateName)
    {
        repo.save(estimateName);
    }

    public void save(Tools tools_to_save, long id)
    {
        EstimateName estimateName = repo.getById(id);
        estimateName.getListTools().add(tools_to_save);
        repo.save(estimateName);
    }

    public Messages delete(long id) {
        try
        {
            repo.deleteById(id);
            return Messages.DELETE;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Messages.NOT_FOUND;
        }
    }

    public EstimateName getNameEstimateById(long id)
    {
        return repo.findById(id).orElse(new EstimateName());
    }

}
