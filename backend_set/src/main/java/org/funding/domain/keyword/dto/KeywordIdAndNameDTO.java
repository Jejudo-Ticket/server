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
public class KeywordIdAndNameDTO {

    private Long id;
    private String name;

    public static KeywordIdAndNameDTO fromVO(KeywordVO vo) {
        return KeywordIdAndNameDTO.builder()
                .id(vo.getKeywordId())
                .name(vo.getName())
                .build();
    }
}
