package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/")
    public Map<String, Object> index() {
        return Map.of(
            "message", "Welcome to Spring Boot!",
            "status", "success"
        );
    }

    @GetMapping("/hello")
    public Map<String, String> sayHello(@RequestParam(defaultValue = "World") String name) {
        return Map.of(
            "greeting", "Hello, " + name + "!"
        );
    }
}
