package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import wns.constants.Messages;
import wns.dto.ToolsDTO;
import wns.entity.Tools;
import wns.repo.ToolsRepo;
import wns.utils.Mapper;

import java.util.List;

@Service
@AllArgsConstructor
public class ToolsService {
    private final ToolsRepo toolsRepo;
    private final ModelMapper modelMapper;

    public List<Tools> getAllTools()
    {
        return toolsRepo.findAll();
    }

    public Messages createTools(ToolsDTO dto) {
        Tools byName = toolsRepo.findByName(dto.getName());
        if(byName == null)
        {
            Tools tools_to_save = modelMapper.map(dto, Tools.class);
            toolsRepo.save(tools_to_save);
            return Messages.TOOLS_CREATE;
        }
        else
            return Messages.TOOLS_EXISTS;
    }
}
