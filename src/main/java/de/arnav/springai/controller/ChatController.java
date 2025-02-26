package de.arnav.springai.controller;

import de.arnav.springai.model.Conversation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface ChatController {

    @GetMapping("/response")
    String getResponse(@RequestParam String message, @RequestParam(required = false) Long conversationId);

    @GetMapping("/chatHistory")
    List<Conversation> getChatHistory();

    @GetMapping("/saveConversation")
    String saveConversation();

    @GetMapping("/conversation")
    Optional<Conversation> getConversationById(@RequestParam Long conversationId);
}
