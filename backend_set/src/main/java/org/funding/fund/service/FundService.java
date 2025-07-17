package org.funding.fund.service;

import lombok.RequiredArgsConstructor;
import org.funding.financialProduct.dao.FinancialProductDAO;
import org.funding.financialProduct.dto.ChallengeDTO;
import org.funding.financialProduct.dto.DonationDTO;
import org.funding.financialProduct.dto.FinancialProductDTO;
import org.funding.financialProduct.dto.LoanDTO;
import org.funding.financialProduct.dto.SavingsDTO;
import org.funding.financialProduct.vo.FinancialProductVO;
import org.funding.fund.dao.FundDAO;
import org.funding.fund.dto.FundCreateDTO;
import org.funding.fund.dto.FundDTO;
import org.funding.fund.vo.FundVO;
import org.funding.fund.vo.enumType.FundType;
import org.funding.fund.vo.enumType.ProgressType;
import org.funding.project.dto.ProjectDTO;
import org.funding.project.service.ProjectService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundService {
    
    private final ProjectService projectService;
    private final FundDAO fundDAO;
    private final FinancialProductDAO financialProductDAO;

    // 펀딩 입력 양식 메서드(백엔드용)
    public Object getFormByProductType(String productType, Long projectId) {
        switch (productType.toLowerCase()) {
            case "savings":
                return createSavingsForm(projectId);
            case "loan":
                return createLoanForm(projectId);
            case "challenge":
                return createChallengeForm(projectId);
            case "donation":
                return createDonationForm(projectId);
            default:
                return null;
        }
    }

    // 펀딩 프로젝트 정보 조회 메서드
    public FundCreateDTO.ProjectInfoResponseDTO getProjectInfoForFunding(Long projectId) {
        ProjectDTO project = projectService.getProjectWithFinancialProduct(projectId);
        if (project == null) {
            return null;
        }
        
        return FundCreateDTO.ProjectInfoResponseDTO.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .promotion(project.getPromotion())
                .deadline(project.getDeadline())
                .createAt(project.getCreateAt())
                .userId(project.getUserId())
                .baseProduct(project.getFinancialProduct())
                .productDetail(project.getProductDetail())
                .build();
    }
    
    public FundCreateDTO.FundCreatedResponseDTO createFund(FundCreateDTO.FundCreateRequestDTO requestDTO) {
        // 1. 프로젝트 정보 조회
        ProjectDTO project = projectService.getProjectWithFinancialProduct(requestDTO.getProjectId());
        if (project == null) {
            throw new IllegalArgumentException("Project not found: " + requestDTO.getProjectId());
        }
        
        // 2. 수정된 금융상품 정보를 기반으로 새로운 상품 생성
        Long newProductId = createModifiedFinancialProduct(project.getFinancialProduct(), requestDTO.getModifiedProductDetail());
        
        // 3. 펀딩 생성 및 DB 저장
        FundVO fundVO = new FundVO();
        fundVO.setProjectId(requestDTO.getProjectId());
        fundVO.setProductId(newProductId);
        fundVO.setProgress(ProgressType.Launch);
        fundVO.setLaunchAt(requestDTO.getLaunchAt());
        fundVO.setEndAt(requestDTO.getEndAt());
        fundVO.setFinancialInstitution(requestDTO.getFinancialInstitution());
        
        // DB에 저장
        FundVO savedFund = fundDAO.insert(fundVO);
        
        // 4. 최종 응답 생성
        return FundCreateDTO.FundCreatedResponseDTO.builder()
                .fundId(savedFund.getFundId())
                .projectId(savedFund.getProjectId())
                .productId(savedFund.getProductId())
                .progress(savedFund.getProgress())
                .launchAt(savedFund.getLaunchAt())
                .endAt(savedFund.getEndAt())
                .financialInstitution(savedFund.getFinancialInstitution())
                .projectTitle(project.getTitle())
                .projectPromotion(project.getPromotion())
                .finalProductDetail(requestDTO.getModifiedProductDetail())
                .build();
    }
    
    private Long createModifiedFinancialProduct(FinancialProductDTO baseProduct, Object modifiedDetail) {
        // 1. 기존 금융상품 정보를 기반으로 새로운 상품 생성
        FinancialProductVO newProduct = new FinancialProductVO();
        newProduct.setName(baseProduct.getName());
        newProduct.setDetail(baseProduct.getDetail());
        newProduct.setFundType(baseProduct.getFundType());
        newProduct.setThumbnail(baseProduct.getThumbnail());
        newProduct.setJoinCondition(baseProduct.getJoinCondition());
        
        // 2. 수정된 상품 정보 반영 (실제로는 modifiedDetail의 타입에 따라 분기 처리)
        if (modifiedDetail != null) {
            // 여기서 modifiedDetail을 기반으로 상품 정보 업데이트
            // 예: SavingsDTO, LoanDTO 등의 필드를 반영
            updateProductFromModifiedDetail(newProduct, modifiedDetail);
        }
        
        // 3. DB에 저장
        FinancialProductVO savedProduct = financialProductDAO.insert(newProduct);
        
        return savedProduct.getProductId();
    }
    
    private void updateProductFromModifiedDetail(FinancialProductVO product, Object modifiedDetail) {
        // 수정된 상세 정보를 기반으로 상품 정보 업데이트
        // 실제 구현에서는 modifiedDetail의 타입을 확인하고 적절히 처리
        if (modifiedDetail instanceof SavingsDTO) {
            SavingsDTO savings = (SavingsDTO) modifiedDetail;
            product.setName(savings.getName());
            product.setDetail(savings.getDetail());
        } else if (modifiedDetail instanceof LoanDTO) {
            LoanDTO loan = (LoanDTO) modifiedDetail;
            product.setName(loan.getName());
            product.setDetail(loan.getDetail());
        } else if (modifiedDetail instanceof ChallengeDTO) {
            ChallengeDTO challenge = (ChallengeDTO) modifiedDetail;
            product.setName(challenge.getName());
            product.setDetail(challenge.getDetail());
        } else if (modifiedDetail instanceof DonationDTO) {
            DonationDTO donation = (DonationDTO) modifiedDetail;
            product.setName(donation.getName());
            product.setDetail(donation.getDetail());
        }
    }
    
    // 펀딩 조회 메서드
    public FundVO getFundById(Long fundId) {
        return fundDAO.selectById(fundId);
    }

    // 펀딩 업데이트 메서드
    public FundVO updateFundProgress(Long fundId, ProgressType progress) {
        FundVO fund = fundDAO.selectById(fundId);
        if (fund != null) {
            fund.setProgress(progress);
            return fundDAO.update(fund);
        }
        return null;
    }
    
    private SavingsDTO createSavingsForm(Long projectId) {
        return SavingsDTO.builder()
                .productId(null)
                .name("적금 상품")
                .periodDays(365)
                .interestRate(3.5)
                .successCondition("매월 정기 납입")
                .build();
    }

    private LoanDTO createLoanForm(Long projectId) {
        return LoanDTO.builder()
                .productId(null)
                .name("대출 상품")
                .loanLimit(10000000L)
                .minInterestRate(2.5)
                .maxInterestRate(5.0)
                .reward("금리 우대")
                .rewardCondition("신용등급 우수시")
                .build();
    }

    private ChallengeDTO createChallengeForm(Long projectId) {
        return ChallengeDTO.builder()
                .productId(null)
                .name("챌린지 상품")
                .challengePeriodDays(30)
                .reward("상금 지급")
                .rewardCondition("챌린지 완료시")
                .build();
    }

    private DonationDTO createDonationForm(Long projectId) {
        return DonationDTO.builder()
                .productId(null)
                .name("기부 상품")
                .recipient("사회복지단체")
                .usagePlan("교육 지원")
                .minDonationAmount(1000)
                .maxDonationAmount(1000000)
                .targetAmount(10000000L)
                .build();
    }
}

