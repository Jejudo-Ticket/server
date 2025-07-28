package org.funding.votes.dao;

import org.funding.votes.dto.VotesRequestDTO;
import org.funding.votes.vo.VotesVO;

import java.util.List;

public interface VotesDAO {

    public VotesVO selectVotes(VotesRequestDTO dto);

    public VotesVO selectVotesById(Long voteId);

    public void insertVotes(VotesVO vo);

    public void deleteVotes(Long voteId);

    List<Long> selectVotedProjectsByUserId(Long userId);

    Long countVotes(Long projectId);

    // 해당 유저가 몇번의 투표를 했는지
    int countVotesByUserId(Long userId);

    //userId, projectId 고려한 투표 삭제
    void deleteVotesByUserIdAndProjectId(Long userId, Long projectId);


}
