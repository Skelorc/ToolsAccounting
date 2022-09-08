package wns.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("write_off")
public class OffsController {

    @GetMapping
    public ModelAndView show()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("write_off");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("write_off_create");
        return modelAndView;
    }
}
