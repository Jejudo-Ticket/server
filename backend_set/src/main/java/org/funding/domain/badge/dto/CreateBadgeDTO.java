package org.funding.domain.badge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBadgeDTO {
    private String name; // 뱃지 이름
    private String description; // 뱃지 설명
    private String autoGrantCondition; // 자동 부여 조건
}
