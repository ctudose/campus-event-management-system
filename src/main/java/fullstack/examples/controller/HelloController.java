package fullstack.examples.controller;

import fullstack.examples.domain.Event;
import fullstack.examples.domain.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, API!";
    }

    @GetMapping("/message")
    public Message getMessage() {
        return new Message("Hello from JSON!");
    }

    @GetMapping("/api/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("API is running");
    }
}
