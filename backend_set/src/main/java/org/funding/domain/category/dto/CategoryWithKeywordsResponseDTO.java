package org.funding.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.funding.domain.keyword.dto.KeywordIdAndNameDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryWithKeywordsResponseDTO {

    private CategoryIdAndNameDTO category;
    private List<KeywordIdAndNameDTO> keywords;

    public static CategoryWithKeywordsResponseDTO from(CategoryIdAndNameDTO category, List<KeywordIdAndNameDTO> keywordList) {
        return CategoryWithKeywordsResponseDTO.builder()
                .category(category)
                .keywords(keywordList)
                .build();
    }
}
