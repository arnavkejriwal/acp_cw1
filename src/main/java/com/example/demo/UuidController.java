package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UuidController {

    @GetMapping("/uuid")
    public String getUuid() {
        // Return the name of the HTML template
        String uuid = "s2795419";
        return "<html><body><h1>"+ uuid+ "</h1></body></html>";
    }
}