package org.funding.fund.vo;

import lombok.Data;

@Data
// 예금
public class SavingsVO {
    private Long depositId; // 예금 id
    private Long productId; // 상품 id
    private String earlyTerminationPolicy; // 예금 조기 해지
}
