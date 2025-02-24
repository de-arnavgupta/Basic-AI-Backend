package de.arnav.springai.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.arnav.springai.model.Conversation;
import de.arnav.springai.repository.ConversationRepository;
import de.arnav.springai.service.ChatService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatModel chatModel;
    private final ConversationRepository conversationRepository;
    private final ObjectMapper objectMapper;

    private List<String> currentConversation = new ArrayList<>();

    public ChatServiceImpl(ChatModel chatModel, ConversationRepository conversationRepository, ObjectMapper objectMapper) {
        this.chatModel = chatModel;
        this.conversationRepository = conversationRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public String getResponse(String message) {
        ChatResponse response = chatModel.call(
                new Prompt(
                        message,
                        VertexAiGeminiChatOptions.builder()
                                .temperature(0.4)
                                .build()
                ));
        String botResponse = response.getResult().getOutput().getContent();
        saveMessageToCurrentConversation(message, botResponse);
        return botResponse;
    }

    @Override
    public List<Conversation> getChatHistory() {
        return conversationRepository.findAllByOrderByTimestampAsc();
    }

    @Override
    public void saveConversation() {
        try {
            String jsonConversation = objectMapper.writeValueAsString(currentConversation);
            conversationRepository.save(new Conversation(jsonConversation));
            currentConversation.clear();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void saveMessageToCurrentConversation(String message, String response) {
        currentConversation.add("You: " + message);
        currentConversation.add("Bot: " + response);
    }
}
