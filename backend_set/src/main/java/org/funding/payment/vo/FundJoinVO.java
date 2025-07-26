package org.funding.payment.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundJoinVO {
    private Long joinId;
    private Long userId;
    private String joinNumber;
    private Long fundId;
    private String fundName;
    private BigDecimal joinAmount;
    private String paymentMethod;
    private String status;
    private LocalDateTime joinDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}