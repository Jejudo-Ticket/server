package org.funding.fund.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChallengeVO {
    private Long challengeId; // 챌린지 id
    private Long productId; // 상품 ID
    private Integer challengePeriodDays; // 챌린지 기간 (일 단위)
    private String reward; // 리워드
    private String rewardCondition; // 리워드 조건
}
