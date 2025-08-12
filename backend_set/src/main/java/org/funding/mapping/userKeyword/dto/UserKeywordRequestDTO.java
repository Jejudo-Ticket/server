package org.funding.mapping.userKeyword.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserKeywordRequestDTO {

    private Long userId;
    private Long keywordId;
}
