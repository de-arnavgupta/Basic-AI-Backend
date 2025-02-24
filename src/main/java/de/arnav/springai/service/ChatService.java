package de.arnav.springai.service;

import de.arnav.springai.model.Conversation;
import java.util.List;

public interface ChatService {
    String getResponse(String message);
    List<Conversation> getChatHistory();
    void saveConversation();
}
