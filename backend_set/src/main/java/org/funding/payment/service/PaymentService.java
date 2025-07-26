package org.funding.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.funding.payment.dto.FundJoinDto;
import org.funding.payment.vo.FundJoinVO;
import org.funding.payment.dao.FundJoinDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final FundJoinDAO fundJoinDAO;

    public void saveFundJoin(Long userId, List<FundJoinDto> fundJoinDtos) {
        try {
            for (FundJoinDto fundJoinDto : fundJoinDtos) {
                FundJoinVO fundJoin = FundJoinVO.builder()
                        .userId(userId)
                        .joinNumber(fundJoinDto.getJoinNumber())
                        .fundId(fundJoinDto.getFundId())
                        .fundName(fundJoinDto.getFundName())
                        .joinAmount(fundJoinDto.getJoinAmount())
                        .paymentMethod(fundJoinDto.getPaymentMethod())
                        .status("COMPLETED")
                        .joinDate(LocalDateTime.now())
                        .build();
                
                fundJoinDAO.insertFundJoin(fundJoin);
                log.info("펀드 가입 저장 완료: {}", fundJoin.getJoinNumber());
            }
        } catch (Exception e) {
            log.error("펀드 가입 저장 실패", e);
            throw new RuntimeException("펀드 가입 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}