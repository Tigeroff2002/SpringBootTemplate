package ru.vlsu.ispi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/home/")
public class HomeController {
    @RequestMapping("index")
    public String Index(){
        return "index";
    }
}
