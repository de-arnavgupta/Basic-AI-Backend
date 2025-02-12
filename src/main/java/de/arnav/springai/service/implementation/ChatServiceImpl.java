package de.arnav.springai.service.implementation;

import de.arnav.springai.service.ChatService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatModel chatModel;

    public ChatServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String chat(String message) {
        return chatModel.call(message);
    }

    @Override
    public String getResponse(String message) {
        return "AI Response: " + message;  // Modify this to call Gemini API
    }
}
