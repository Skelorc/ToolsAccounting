package wns.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sale")
public class SaleController {

    @GetMapping
    public ModelAndView show()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sale");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sale_create");
        return modelAndView;
    }
}
