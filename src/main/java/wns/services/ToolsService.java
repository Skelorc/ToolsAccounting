package wns.services;

import org.springframework.stereotype.Service;
import wns.constants.Answers;
import wns.entity.Tools;
import wns.repo.ToolsRepo;

import java.util.List;

@Service
public class ToolsService {
    private final ToolsRepo toolsRepo;

    public ToolsService(ToolsRepo toolsRepo) {
        this.toolsRepo = toolsRepo;
    }

    public List<Tools> getAllTools()
    {
        return toolsRepo.findAll();
    }

    public String  createTools(Tools tools) {
        Tools byName = toolsRepo.findByName(tools.getName());
        if(byName == null)
        {
            toolsRepo.save(tools);
            return Answers.TOOLS_CREATE.getValue();
        }
        else
            return Answers.TOOLS_EXISTS.getValue();
    }
}
