package org.funding.domain.comment.dao;

import org.apache.ibatis.annotations.Mapper;
import org.funding.domain.comment.vo.CommentVO;

import java.util.List;

@Mapper
public interface CommentDAO {

    public void insertComment(CommentVO commentVO);

    public void deleteComment(Long commentId);

    public CommentVO getCommentById(Long commentId);

    List<CommentVO> getAllCommentsByProjectId(Long projectId);

    List<CommentVO> getAllCommentsByFundingId(Long fundingId);
}
