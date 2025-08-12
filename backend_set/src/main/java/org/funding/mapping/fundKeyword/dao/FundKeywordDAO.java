package org.funding.mapping.fundKeyword.dao;

import org.funding.mapping.fundKeyword.dto.FundKeywordRequestDTO;
import org.funding.mapping.fundKeyword.vo.FundKeywordVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundKeywordDAO {

    FundKeywordVO findFundKeywordMapping(FundKeywordRequestDTO requestDTO);

    List<Long> selectKeywordIdsByFundId(Long fundId);

    void insertFundKeyword(FundKeywordRequestDTO requestDTO);

    void deleteFundKeyword(FundKeywordRequestDTO requestDTO);
}