package wns.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wns.dto.ToolsDTO;

@Controller()
@RequestMapping("acceptance")
public class AcceptanceController {

    @GetMapping
    public String show()
    {
        return "acceptance";
    }

    @GetMapping("/create")
    public String showClose(Model model)
    {
        model.addAttribute("tool",new ToolsDTO());
        return "acceptance_close";
    }
}
