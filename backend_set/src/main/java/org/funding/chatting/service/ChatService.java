package org.funding.chatting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.funding.chatting.dao.ChatDAO;
import org.funding.chatting.dto.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatDAO chatDAO;

    public void saveMessage(ChatMessage message) {
        log.info("메시지 저장 요청: {}", message);
        chatDAO.saveMessage(message);

    }

    public List<ChatMessage> getMessages(Long roomId) {
        return chatDAO.findMessagesByRoomId(roomId);
    }
}
