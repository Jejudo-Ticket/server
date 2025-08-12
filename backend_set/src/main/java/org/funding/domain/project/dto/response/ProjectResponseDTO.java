package org.funding.domain.project.dto.response;

import lombok.Data;
import org.funding.global.S3.vo.S3ImageVO;
import org.funding.domain.project.vo.ProjectVO;

import java.util.List;

@Data
public class ProjectResponseDTO {

    private ProjectVO basicInfo;
    private Object detailInfo;
    private Long voteCount;
    private List<S3ImageVO> imageList;

}