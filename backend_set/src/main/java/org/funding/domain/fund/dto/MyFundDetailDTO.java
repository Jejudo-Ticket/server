package org.funding.domain.fund.dto;

import lombok.Data;
import org.funding.domain.fund.vo.enumType.FundType;
import org.funding.domain.fund.vo.enumType.ProgressType;

import java.time.LocalDateTime;

@Data
public class MyFundDetailDTO {
    private Long fundId;
    private ProgressType progress;
    private LocalDateTime launchAt;
    private LocalDateTime endAt;
    private String financialInstitution;

    private String productName;
    private FundType fundType;

    private String productImageUrl;
}
