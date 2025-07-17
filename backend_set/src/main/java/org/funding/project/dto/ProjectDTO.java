package org.funding.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.funding.financialProduct.dto.FinancialProductDTO;
import org.funding.project.vo.enumType.Feed;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {
    private Long projectId;
    private String title;
    private String promotion;
    private Feed projectType;
    private LocalDateTime deadline;
    private String rejectionReason;
    private LocalDateTime createAt;
    private Long userId;
    private Long productId;
    
    // 연관된 금융상품 정보
    private FinancialProductDTO financialProduct;
    
    // 상세 금융상품 정보 (productType에 따라 다름)
    private Object productDetail;
}