package org.funding.domain.project.dto.response;

import lombok.Data;
import org.funding.global.S3.vo.S3ImageVO;
import org.funding.domain.project.vo.enumType.ProjectProgress;
import org.funding.domain.project.vo.enumType.ProjectType;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TopProjectDTO {
    private Long projectId;
    private ProjectType projectType;
    private String title;
    private String promotion;
    private ProjectProgress progress;
    private LocalDateTime deadline;
    private LocalDateTime createAt;
    private Integer voteCount;
    private List<S3ImageVO> images;
}
