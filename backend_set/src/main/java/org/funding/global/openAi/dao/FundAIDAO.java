package org.funding.global.openAi.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.funding.domain.fund.vo.FundVO;
import org.funding.domain.fund.vo.enumType.FundType;

import java.util.List;

@Mapper
public interface FundAIDAO {

    FundVO findFundById(Long fundId);

    FundType findFundTypeByProductId(Long productId);

    List<FundVO> findFundsByFundTypeExcludeSelf(@Param("fundType") String fundType, @Param("fundId") Long fundId);

}
