package org.funding.mapping.userLoan.dto;

import lombok.Data;
import org.funding.mapping.userLoan.vo.enumType.SuccessType;

@Data
public class ApproveUserLoanRequestDTO {
    private SuccessType type;
    private Long userLoanId;
}
