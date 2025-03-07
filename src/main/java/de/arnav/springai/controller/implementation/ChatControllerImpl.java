package de.arnav.springai.controller.implementation;

import de.arnav.springai.controller.ChatController;
import de.arnav.springai.model.Conversation;
import de.arnav.springai.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ChatControllerImpl implements ChatController {

    private final ChatService chatService;

    public ChatControllerImpl(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public String getResponse(@RequestParam String message, @RequestParam(required = false) Long conversationId) {
        return chatService.getResponse(message, conversationId);
    }

    @Override
    public List<Conversation> getChatHistory() {
        return chatService.getChatHistory();
    }

    @Override
    public String saveConversation() {
        chatService.saveConversation();
        return "Conversation saved!";
    }

    @Override
    public Optional<Conversation> getConversationById(@RequestParam Long conversationId) {
        return chatService.getConversationById(conversationId);
    }
}
