package com.rich.bryan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RootController {

    @GetMapping("/")
    public String books(){
        return "redirect:/list/myBooks";
    }
}