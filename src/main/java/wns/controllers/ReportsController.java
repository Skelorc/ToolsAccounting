package wns.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wns.constants.Messages;
import wns.constants.Roles;
import wns.entity.Category;
import wns.entity.User;
import wns.services.ReportsService;
import wns.utils.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("reports")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportsService reportsService;
    @GetMapping
    public String show() {
        return "reports";
    }

    @GetMapping("/test-get")
    public ResponseEntity<?> createTestGet()
    {
        Map<String, User> data = new HashMap<>();
        data.put("some_key", new User());
        User us = new User();
        us.setUsername("Ruslan");
        us.setPassword("some");
        us.setRoles(Roles.ADMIN);
        data.put("admin",us);
        return ResponseHandler.generateResponse(Messages.OK,"тут мы писали урл для редиректа, если надо!", data);
    }

    @PostMapping("/test-post")
    public ResponseEntity<?> createTestPost(@RequestBody(required = false) String param)
    {
        System.out.println(param);
        Map<String, List<Category>> data = new HashMap<>();
        List<Category> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Category category = new Category();
            category.setCode("123 " + i);
            category.setName("Name category");
            category.setId(i);
            list.add(category);
        }
        data.put("content", list);
        return ResponseHandler.generateResponse(Messages.OK,"some",data);

    }
}
