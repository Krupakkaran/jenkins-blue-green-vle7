// src/main/java/com/krupakkaran/SampleJavaAppApplication.java

package com.krupakkaran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SampleJavaAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleJavaAppApplication.class, args);
    }

    // Simple endpoint to show version and color (for validation)
    @GetMapping("/")
    public String home() {
        return "Hello from Sample Java App! Version: V1 (Blue) - Running on Port 8080";
    }
}
