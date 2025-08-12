package org.funding.mapping.userSaving.dto;

import lombok.Data;

@Data
public class UserSavingRequestDTO {
    private Long fundId; // 펀딩 id
    private Integer savingAmount; // 저축 금액
}
