package org.funding.domain.project.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.funding.domain.project.vo.LoanProjectVO;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateLoanProjectRequestDTO extends CreateProjectRequestDTO {

    // Loan 고유 칼럼
    private Long loanLimit; // 대출 한도
    private BigDecimal desiredInterestRate; // 희망 금리
    private String reward; // 리워드
    private String rewardCondition; // 리워드 조건

    public LoanProjectVO toLoanVO() {
        return LoanProjectVO.builder()
                .loanLimit(this.getLoanLimit())
                .desiredInterestRate(this.getDesiredInterestRate())
                .reward(this.getReward())
                .rewardCondition(this.getRewardCondition())
                .build();
    }
}
