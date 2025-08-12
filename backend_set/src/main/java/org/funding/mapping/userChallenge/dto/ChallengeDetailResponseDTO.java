package org.funding.mapping.userChallenge.dto;

import lombok.Data;
import org.funding.mapping.challengeLog.vo.ChallengeLogVO;

import java.util.List;

@Data
public class ChallengeDetailResponseDTO {

    private UserChallengeDetailDTO challengeInfo;
    private List<ChallengeLogVO> dailyLogs;
}
