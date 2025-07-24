package org.funding.chatting.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.funding.chatting.dto.ChatMessage;

import java.util.List;

@Mapper
public interface ChatDAO {

    void saveMessage(ChatMessage message);

    List<ChatMessage> findMessagesByRoomId(@Param("roomId") Long roomId);
}
