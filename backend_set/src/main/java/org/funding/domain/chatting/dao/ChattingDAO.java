package org.funding.domain.chatting.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.funding.domain.chatting.dto.ChattingMessage;

import java.util.List;

@Mapper
public interface ChattingDAO {

    void saveMessage(ChattingMessage message);

    List<ChattingMessage> findMessagesByRoomId(@Param("projectId") Long projectId);
}