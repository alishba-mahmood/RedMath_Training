package com.redmath.workshop.helloworld;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/helloworld")
public class HelloWorldController {

    @GetMapping
    public ResponseEntity<Map<String,String>> helloworld()
    {
        return ResponseEntity.ok(Map.of("message","Hello World @ " + LocalDateTime.now()));
    }
}
