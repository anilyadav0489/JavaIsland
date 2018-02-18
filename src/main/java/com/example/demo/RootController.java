package com.example.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class RootController {

	@CrossOrigin(origins = "*")
	@RequestMapping("/")
	String home() {
        return "Hello World!";
    }
}
