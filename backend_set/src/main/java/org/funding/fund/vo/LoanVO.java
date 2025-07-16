package org.funding.fund.vo;

import lombok.Data;

@Data
// 적금
public class LoanVO {
    private Long installmentId; // 적금 id
    private Long productId;
    private String regularDepositPlan; // 적금 계획
}
