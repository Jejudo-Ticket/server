package org.funding.financialProduct.dto;

import org.funding.fund.dto.FundDTO;

public class SavingsDTO extends FundDTO {
    private Integer periodDays;
    private Double interestRate;
    private String successCondition;
}
