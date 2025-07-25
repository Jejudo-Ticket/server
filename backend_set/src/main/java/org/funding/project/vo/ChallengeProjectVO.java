package org.funding.project.vo;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeProjectVO {

    private Long projectId;

    // Challenge 고유 칼럼
    private Long challengePeriodDays; // 챌린지 기간
    private String reward; // 리워드
    private String rewardCondition; // 리워드 조건
}
