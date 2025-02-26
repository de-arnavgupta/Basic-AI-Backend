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
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatModel chatModel;
    private final ConversationRepository conversationRepository;
    private final ObjectMapper objectMapper;
    private List<String> currentConversation = new ArrayList<>();
    private Long activeConversationId = null;

    public ChatServiceImpl(ChatModel chatModel, ConversationRepository conversationRepository, ObjectMapper objectMapper) {
        this.chatModel = chatModel;
        this.conversationRepository = conversationRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public String getResponse(String message, Long conversationId) {
        Conversation conversation;

        if (conversationId != null) {
            Optional<Conversation> existingConversation = conversationRepository.findById(conversationId);
            if (existingConversation.isPresent()) {
                conversation = existingConversation.get();
                activeConversationId = conversationId;
                currentConversation = loadMessagesFromJson(conversation.getMessages());
            }
            else
            {
                conversation = new Conversation();
                activeConversationId = null;
            }
        }
        else
        {
            conversation = new Conversation();
            activeConversationId = null;
        }

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
            if (activeConversationId != null) {
                Conversation existingConversation = conversationRepository.findById(activeConversationId).orElseThrow();
                existingConversation.setMessages(jsonConversation);
                conversationRepository.save(existingConversation);
            }
            else
            {
                conversationRepository.save(new Conversation(jsonConversation));
            }
            currentConversation.clear();
            activeConversationId = null;
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Conversation> getConversationById(Long id) {
        return conversationRepository.findById(id);
    }

    private void saveMessageToCurrentConversation(String message, String response) {
        currentConversation.add("You: " + message);
        currentConversation.add("Bot: " + response);
    }

    private List<String> loadMessagesFromJson(String json) {
        try {
            return objectMapper.readValue(json, List.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
