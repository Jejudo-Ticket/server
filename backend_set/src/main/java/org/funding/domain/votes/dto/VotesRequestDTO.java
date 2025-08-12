package org.funding.domain.votes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.funding.domain.votes.vo.VotesVO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotesRequestDTO {
    private Long userId;
    private Long projectId;

    public VotesVO toVO() {
        return VotesVO.builder()
                .userId(getUserId())
                .projectId(getProjectId())
                .build();

    }
}
