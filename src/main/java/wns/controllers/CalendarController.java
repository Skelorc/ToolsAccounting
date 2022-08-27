package wns.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("calendar")
public class CalendarController {

    @GetMapping
    public ModelAndView show()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("calendar");
        return modelAndView;
    }
}
