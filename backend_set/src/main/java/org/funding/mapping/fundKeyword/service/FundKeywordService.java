package org.funding.mapping.fundKeyword.service;

import lombok.RequiredArgsConstructor;
import org.funding.domain.keyword.dao.KeywordDAO;
import org.funding.domain.keyword.vo.KeywordVO;
import org.funding.domain.fund.dao.FundDAO;
import org.funding.mapping.fundKeyword.dao.FundKeywordDAO;
import org.funding.mapping.fundKeyword.dto.FundKeywordRequestDTO;
import org.funding.mapping.fundKeyword.vo.FundKeywordVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FundKeywordService {

    private final FundKeywordDAO fundKeywordDAO;
    private final KeywordDAO keywordDAO;
    private final FundDAO fundDAO;

    public List<KeywordVO> findKeywordIdsByFundId(Long fundId) {
        List<Long> keywordIdList = fundKeywordDAO.selectKeywordIdsByFundId(fundId);

        return keywordIdList.stream().map(keywordDAO::selectKeywordById).toList();
    }

    public void mapFundKeyword(FundKeywordRequestDTO requestDTO) {
        FundKeywordVO fundKeywordVO = fundKeywordDAO.findFundKeywordMapping(requestDTO);

        if (fundKeywordVO != null) {
            // 이미 매핑되어 있음
            return;
        }

        fundKeywordDAO.insertFundKeyword(requestDTO);
    }

    public void unmapFundKeyword(FundKeywordRequestDTO requestDTO) {
        FundKeywordVO fundKeywordVO = fundKeywordDAO.findFundKeywordMapping(requestDTO);

        if (fundKeywordVO == null) {
            // 이미 매핑되어 있지 않음
            return;
        }

        fundKeywordDAO.deleteFundKeyword(requestDTO);
    }
}