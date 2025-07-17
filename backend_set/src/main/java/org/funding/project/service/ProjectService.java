package org.funding.project.service;

import lombok.RequiredArgsConstructor;
import org.funding.financialProduct.dao.FinancialProductDAO;
import org.funding.financialProduct.dto.*;
import org.funding.financialProduct.vo.FinancialProductVO;
import org.funding.fund.vo.enumType.FundType;
import org.funding.project.dao.ProjectDAO;
import org.funding.project.dto.ProjectDTO;
import org.funding.project.vo.ProjectVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    
    private final ProjectDAO projectDAO;
    private final FinancialProductDAO financialProductDAO;
    
    public ProjectDTO getProjectWithFinancialProduct(Long projectId) {
        // 1. 프로젝트 조회
        ProjectVO projectVO = projectDAO.selectById(projectId);
        if (projectVO == null) {
            // 데이터가 없으면 Mock 데이터 사용 (개발/테스트용)
            projectVO = createMockProject(projectId);
            projectDAO.insert(projectVO);
        }
        
        // 2. 금융상품 조회
        FinancialProductVO financialProductVO = financialProductDAO.selectById(projectVO.getProductId());
        if (financialProductVO == null) {
            // 데이터가 없으면 Mock 데이터 사용 (개발/테스트용)
            financialProductVO = createMockFinancialProduct(projectVO.getProductId());
            financialProductDAO.insert(financialProductVO);
        }
        
        // FinancialProductDTO 설정
        FinancialProductDTO financialProductDTO = FinancialProductDTO.builder()
                .productId(financialProductVO.getProductId())
                .name(financialProductVO.getName())
                .detail(financialProductVO.getDetail())
                .fundType(financialProductVO.getFundType())
                .thumbnail(financialProductVO.getThumbnail())
                .joinCondition(financialProductVO.getJoinCondition())
                .build();
        
        // 상세 금융상품 정보 설정
        Object productDetail = getProductDetail(financialProductVO.getFundType(), projectVO.getProductId());
        
        return ProjectDTO.builder()
                .projectId(projectVO.getProjectId())
                .title(projectVO.getTitle())
                .promotion(projectVO.getPromotion())
                .projectType(projectVO.getProjectType())
                .deadline(projectVO.getDeadline())
                .rejectionReason(projectVO.getRejectionReason())
                .createAt(projectVO.getCreateAt())
                .userId(projectVO.getUserId())
                .productId(projectVO.getProductId())
                .financialProduct(financialProductDTO)
                .productDetail(productDetail)
                .build();
    }
    
    private Object getProductDetail(FundType fundType, Long productId) {
        switch (fundType) {
            case Savings:
                return mockGetSavingsDetail(productId);
            case Loan:
                return mockGetLoanDetail(productId);
            case Challenge:
                return mockGetChallengeDetail(productId);
            case Donation:
                return mockGetDonationDetail(productId);
            default:
                return null;
        }
    }
    
    // Mock 데이터 생성 메서드들 (개발/테스트용)
    private ProjectVO createMockProject(Long projectId) {
        ProjectVO project = new ProjectVO();
        project.setProjectId(projectId);
        project.setTitle("제주도 여행 프로젝트");
        project.setPromotion("제주도 여행을 위한 적금 프로젝트입니다.");
        project.setProductId(1L);
        project.setUserId(1L);
        project.setCreateAt(LocalDateTime.now());
        project.setDeadline(LocalDateTime.now().plusDays(30));
        return project;
    }
    
    private FinancialProductVO createMockFinancialProduct(Long productId) {
        FinancialProductVO product = new FinancialProductVO();
        product.setProductId(productId);
        product.setName("제주도 여행 적금");
        product.setDetail("제주도 여행을 위한 특별 적금 상품");
        product.setFundType(FundType.Savings);
        product.setThumbnail("thumbnail.jpg");
        product.setJoinCondition("만 19세 이상");
        return product;
    }
    
    /**
     * 프로젝트 생성
     */
    public ProjectVO createProject(ProjectVO projectVO) {
        return projectDAO.insert(projectVO);
    }
    
    /**
     * 프로젝트 업데이트
     */
    public ProjectVO updateProject(ProjectVO projectVO) {
        return projectDAO.update(projectVO);
    }
    
    /**
     * 사용자별 프로젝트 목록 조회
     */
    public List<ProjectVO> getProjectsByUserId(Long userId) {
        return projectDAO.selectByUserId(userId);
    }
    
    private SavingsDTO mockGetSavingsDetail(Long productId) {
        return SavingsDTO.builder()
                .productId(productId)
                .name("제주도 여행 적금")
                .periodDays(365)
                .interestRate(3.5)
                .successCondition("매월 정기 납입")
                .build();
    }
    
    private LoanDTO mockGetLoanDetail(Long productId) {
        return LoanDTO.builder()
                .productId(productId)
                .name("제주도 여행 대출")
                .loanLimit(5000000L)
                .minInterestRate(2.5)
                .maxInterestRate(4.5)
                .build();
    }
    
    private ChallengeDTO mockGetChallengeDetail(Long productId) {
        return ChallengeDTO.builder()
                .productId(productId)
                .name("제주도 여행 챌린지")
                .challengePeriodDays(30)
                .reward("여행 쿠폰")
                .rewardCondition("챌린지 완료시")
                .build();
    }
    
    private DonationDTO mockGetDonationDetail(Long productId) {
        return DonationDTO.builder()
                .productId(productId)
                .name("제주도 환경 보호 기부")
                .recipient("제주도 환경재단")
                .usagePlan("제주도 환경 보호")
                .minDonationAmount(1000)
                .maxDonationAmount(1000000)
                .targetAmount(1000000L)
                .build();
    }
}