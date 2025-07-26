package org.funding.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundJoinDto {
    private String joinNumber;
    private Long fundId;
    private String fundName;
    private BigDecimal joinAmount;
    private String paymentMethod;
}