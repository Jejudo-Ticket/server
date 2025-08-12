package org.funding.domain.comment.dto;

import lombok.Data;
import org.funding.domain.comment.vo.enumType.TargetType;

@Data
public class findAllCommentsRequestDTO {

    private TargetType targetType;
    private Long targetId;

}
