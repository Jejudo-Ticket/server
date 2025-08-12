package org.funding.domain.project.dto.response;

import java.math.BigDecimal;

public class SavingsProjectResponseDTO{

    // Savings 고유 칼럼
    private Long periodDays; // 상품기간
    private BigDecimal interestRate; // 연이율 (%)
    private String successCondition; // 목표 달성 조건
}
