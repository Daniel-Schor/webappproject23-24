package dev.eventplaner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @GetMapping(value = "/home")
    public String home() {
        return "index.html";
    }

}
