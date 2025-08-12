package org.funding.domain.keyword.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.funding.domain.keyword.vo.KeywordVO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeywordResponseDTO {

    private Long interestId;
    private String keyword;

    public static KeywordResponseDTO fromVO(KeywordVO keywordVO) {
        return KeywordResponseDTO.builder()
                .interestId(keywordVO.getKeywordId())
                .keyword(keywordVO.getName())
                .build();
    }
}
