package org.funding.mapping.userChallenge.vo;

import lombok.Data;
import org.funding.mapping.userChallenge.vo.enumType.ChallengeStatus;

@Data
public class UserChallengeVO {
    private Long userChallengeId;
    private Long fundId;
    private Long userId;
    private int currentCount;
    private int failCount;
    private ChallengeStatus challengeStatus; // 진행 상태
}
