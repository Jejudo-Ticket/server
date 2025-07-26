package org.funding.payment.dao;

import org.funding.payment.vo.FundJoinVO;
import java.util.List;

public interface FundJoinDAO {
    void insertFundJoin(FundJoinVO fundJoin);
    FundJoinVO selectFundJoinByJoinNumber(String joinNumber);
    List<FundJoinVO> selectFundJoinsByUserId(Long userId);
    List<FundJoinVO> selectFundJoinsByUserIdAndStatus(Long userId, String status);
}