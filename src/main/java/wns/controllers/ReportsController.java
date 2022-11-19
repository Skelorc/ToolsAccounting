package wns.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wns.services.ReportsService;

@Controller
@RequestMapping("reports")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportsService reportsService;


    @GetMapping
    public String show() {
        return "reports";
    }

    @GetMapping("z")
    public String showTest(@RequestParam(value = "data", required = false) String test) {
        //reportsService.createTest();
        return "reports";
    }
}
