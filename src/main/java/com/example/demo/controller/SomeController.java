package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class SomeController {
    @GetMapping
    public String test() {
        return "a7eeh";
    }

    @PostMapping("2")
    public String test2() {
        return "a7eeh";
    }
}
