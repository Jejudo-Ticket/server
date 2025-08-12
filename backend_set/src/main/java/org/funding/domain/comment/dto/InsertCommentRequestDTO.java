package org.funding.domain.comment.dto;

import lombok.Data;
import org.funding.domain.comment.vo.CommentVO;
import org.funding.domain.comment.vo.enumType.TargetType;

@Data
public class InsertCommentRequestDTO {

    private String content;

    private TargetType targetType;
    private Long targetId;


    public CommentVO toVO() {
        CommentVO commentVO = new CommentVO();

        commentVO.setContent(content);
        commentVO.setTargetType(targetType);
        commentVO.setTargetId(targetId);

        return commentVO;
    }
}
