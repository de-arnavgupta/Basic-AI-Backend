package de.arnav.springai.service.implementation;

import de.arnav.springai.service.ChatService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatModel chatModel;

    public ChatServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String chat(String message)
    {
        return chatModel.call(message);
    }

    @Override
    public String getResponse(String message)
    {
        ChatResponse response = chatModel.call(
                new Prompt(
                        message,
                        VertexAiGeminiChatOptions.builder()
                                .temperature(0.4)
                                .build()
                ));
        return response.getResult().getOutput().getContent();
    }
}
