package org.funding.project.dao;

import org.funding.project.vo.ProjectVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectDAO {
    
    /**
     * 프로젝트 생성
     */
    ProjectVO insert(ProjectVO projectVO);
    
    /**
     * 프로젝트 조회 by ID
     */
    ProjectVO selectById(Long projectId);
    
    /**
     * 사용자 ID로 프로젝트 목록 조회
     */
    List<ProjectVO> selectByUserId(Long userId);
    
    /**
     * 프로젝트 업데이트
     */
    ProjectVO update(ProjectVO projectVO);
    
    /**
     * 프로젝트 삭제
     */
    boolean delete(Long projectId);
    
    /**
     * 모든 프로젝트 조회
     */
    List<ProjectVO> selectAll();
}