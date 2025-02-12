package de.arnav.springai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ChatController {

    @GetMapping("/chat")
    String chat(@RequestParam String message);

    @GetMapping("/response")
    String getResponse(@RequestParam String message);
}
