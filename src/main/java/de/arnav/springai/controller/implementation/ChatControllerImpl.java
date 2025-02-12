package de.arnav.springai.controller.implementation;

import de.arnav.springai.controller.ChatController;
import de.arnav.springai.service.ChatService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatControllerImpl implements ChatController {

    private final ChatService chatService;

    public ChatControllerImpl(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public String chat(@RequestParam String message) {
        return chatService.chat(message);
    }

    @Override
    public String getResponse(@RequestParam String message) {
        return chatService.getResponse(message);
    }
}
