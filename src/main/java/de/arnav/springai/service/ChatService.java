package de.arnav.springai.service;

import de.arnav.springai.model.Conversation;
import java.util.List;
import java.util.Optional;

public interface ChatService
{
    String getResponse(String message, Long conversationId);
    List<Conversation> getChatHistory();
    void saveConversation();
    Optional<Conversation> getConversationById(Long id);
}
