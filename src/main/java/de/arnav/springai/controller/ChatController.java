package de.arnav.springai.controller;

import de.arnav.springai.model.Conversation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ChatController {

    @GetMapping("/response")
    String getResponse(@RequestParam String message);

    @GetMapping("/chatHistory")
    List<Conversation> getChatHistory();

    @GetMapping("/saveConversation")
    String saveConversation();
}
