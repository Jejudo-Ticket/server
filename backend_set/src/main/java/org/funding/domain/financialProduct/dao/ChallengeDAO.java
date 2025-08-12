package org.funding.domain.financialProduct.dao;

import org.funding.domain.financialProduct.vo.ChallengeVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChallengeDAO {
    void insertChallenge(ChallengeVO vo);
    ChallengeVO selectByProductId(Long productId);
    void update(ChallengeVO vo);
    void deleteByProductId(Long productId);
}
