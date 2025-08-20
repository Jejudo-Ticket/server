package org.funding.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.funding.comment.dto.CommentResponseDTO;
import org.funding.comment.dto.InsertCommentRequestDTO;
import org.funding.comment.service.CommentService;

import org.funding.comment.vo.enumType.TargetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private ObjectMapper objectMapper;
    private final Long MOCK_USER_ID = 1L;

    @BeforeEach
    void setUp() {
        // LocalDateTime 직렬화를 위해 JavaTimeModule 추가
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(commentController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper), new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .build();
    }

    @Test
    @DisplayName("댓글 작성 API")
    void addComment() throws Exception {
        // given
        InsertCommentRequestDTO requestDTO = new InsertCommentRequestDTO();
        requestDTO.setContent("새로운 댓글입니다.");
        requestDTO.setTargetType(TargetType.Project);
        requestDTO.setTargetId(10L);

        CommentResponseDTO responseDTO = new CommentResponseDTO();
        responseDTO.setCommentId(99L);
        responseDTO.setUserId(MOCK_USER_ID);
        responseDTO.setContent("새로운 댓글입니다.");
        responseDTO.setCreateAt(LocalDateTime.now());

        given(commentService.addComment(any(InsertCommentRequestDTO.class), anyLong())).willReturn(responseDTO);

        // when & then
        mockMvc.perform(post("/api/comment")
                        .requestAttr("userId", MOCK_USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(99L))
                .andExpect(jsonPath("$.content").value("새로운 댓글입니다."));
    }

    @Test
    @DisplayName("댓글 삭제 API")
    void deleteComment() throws Exception {
        // given
        Long commentId = 1L;
        // void를 반환하는 서비스 메서드는 doNothing()으로 Mocking
        doNothing().when(commentService).deleteComment(commentId);

        // when & then
        mockMvc.perform(delete("/api/comment")
                        .requestAttr("userId", MOCK_USER_ID)
                        .param("commentId", String.valueOf(commentId))) // @RequestParam은 .param() 사용
                .andDo(print())
                .andExpect(status().isNoContent()); // 204 No Content 상태 코드 확인

        // deleteComment 메서드가 올바른 인자와 함께 호출되었는지 검증
        verify(commentService).deleteComment(commentId);
    }

    @Test
    @DisplayName("댓글 목록 조회 API")
    void getAllComments() throws Exception {
        // given
        Long targetId = 10L;
        TargetType targetType = TargetType.Project;

        CommentResponseDTO responseDTO = new CommentResponseDTO();
        responseDTO.setContent("조회된 댓글");
        List<CommentResponseDTO> responseList = Collections.singletonList(responseDTO);

        given(commentService.findAllComments(targetType, targetId)).willReturn(responseList);

        // when & then
        mockMvc.perform(get("/api/comment")
                        .requestAttr("userId", MOCK_USER_ID)
                        .param("targetType", targetType.name())
                        .param("targetId", String.valueOf(targetId))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].content").value("조회된 댓글"));
    }
}