package org.funding.mapping.projectKeyword.dao;


import org.funding.mapping.projectKeyword.dto.ProjectKeywordRequestDTO;
import org.funding.mapping.projectKeyword.vo.ProjectKeywordVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectKeywordDAO {

    ProjectKeywordVO findProjectKeywordMapping(ProjectKeywordRequestDTO requestDTO);

    List<Long> selectKeywordIdsByProjectId(Long projectId);

    void insertProjectKeyword(ProjectKeywordRequestDTO requestDTO);

    void deleteProjectKeyword(ProjectKeywordRequestDTO requestDTO);

    List<Long> selectProjectIdsByKeywordIds(List<Long> keywordIds);
}
