package org.funding.mapping.userDonation.dto;

import lombok.Data;

@Data
public class DonateRequestDTO {
    private Long fundId;
    private Integer donateAmount;
    private boolean anonymous; // 익명 여부
}
