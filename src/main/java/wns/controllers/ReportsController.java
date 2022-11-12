package wns.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("reports")
public class ReportsController {

    Logger logger = LoggerFactory.getLogger(ReportsController.class);

    @GetMapping
    public String show()
    {
        return "reports";
    }

    @GetMapping("z")
    public String showTest(@RequestParam(value = "data", required = false) String test)
    {
        System.out.println(test);
        logger.warn(test);
        logger.error(test);
        return "reports";
    }
}
