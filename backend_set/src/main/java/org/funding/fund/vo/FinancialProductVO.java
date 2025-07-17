package org.funding.fund.vo;

import org.funding.fund.vo.enumType.FundType;

import java.math.BigDecimal;

public class FinancialProductVO {
    private Long productId; // 상품 id
    private String name; // 상품 이름
    private String detail; // 상품 내용
    private FundType fundType; // ENUM: "저축", "대출", "챌린지", "기부"

    // 하위 타입 포함 (선택)
    private SavingsVO savings;
    private LoanVO loan;
    private ChallengeVO challenge;
    private DonationVO donation;
}
