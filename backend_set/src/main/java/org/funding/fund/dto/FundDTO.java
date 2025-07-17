package org.funding.fund.dto;

public class FundDTO {

    public static class FundCreateRequestDTO{
        private Long projectId;     // 유저가 선택
        private String productType;  // 프로젝트 테이블 연결 전
    }
}
