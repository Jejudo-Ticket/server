package org.funding.mapping.userDonation.service;

import lombok.RequiredArgsConstructor;
import org.funding.global.S3.dao.S3ImageDAO;
import org.funding.global.S3.vo.S3ImageVO;
import org.funding.global.S3.vo.enumType.ImageType;
import org.funding.domain.badge.service.BadgeService;
import org.funding.domain.financialProduct.dao.DonationDAO;
import org.funding.domain.financialProduct.vo.DonationVO;
import org.funding.domain.fund.dao.FundDAO;
import org.funding.domain.fund.vo.FundVO;
import org.funding.domain.fund.vo.enumType.ProgressType;
import org.funding.domain.user.dao.MemberDAO;
import org.funding.domain.user.vo.MemberVO;
import org.funding.domain.user.vo.enumType.Role;
import org.funding.mapping.userDonation.dao.UserDonationDAO;
import org.funding.mapping.userDonation.dto.DonateRequestDTO;
import org.funding.mapping.userDonation.dto.DonateResponseDTO;
import org.funding.mapping.userDonation.dto.UserDonationDetailDTO;
import org.funding.mapping.userDonation.vo.UserDonationVO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDonationService {
    private final MemberDAO memberDAO;
    private final UserDonationDAO userDonationDAO;
    private final DonationDAO donationDAO;
    private final FundDAO fundDAO;
    private final BadgeService badgeService;
    private final S3ImageDAO s3ImageDAO;

    // 기부
    public DonateResponseDTO donate(DonateRequestDTO donateRequestDTO, Long userId) {
        Long fundId = donateRequestDTO.getFundId();

        MemberVO member = validateMember(userId);
        FundVO fund = validateFund(fundId);

        Long productId = fund.getProductId();

        // 기부 금액 검증
        DonationVO donation = donationDAO.selectByProductId(productId);
        Integer maxAmount = donation.getMaxDonationAmount();
        Integer minAmount = donation.getMinDonationAmount();

        if (donateRequestDTO.getDonateAmount() > maxAmount || donateRequestDTO.getDonateAmount() < minAmount) {
            throw new RuntimeException("해당 금액은 기부 금액 범위에 벗어났습니다.");
        }

        UserDonationVO userDonationVO = new UserDonationVO();
        userDonationVO.setUserId(userId);
        userDonationVO.setDonationAmount(donateRequestDTO.getDonateAmount());
        userDonationVO.setAnonymous(donateRequestDTO.isAnonymous());
        userDonationDAO.insertUserDonation(userDonationVO);

        // 뱃지 권한 검증 (기부 참여)
        badgeService.checkAndGrantBadges(userId);

        DonateResponseDTO donateResponseDTO = new DonateResponseDTO();
        donateResponseDTO.setUserName(member.getUsername());
        donateResponseDTO.setDonateAmount(donateRequestDTO.getDonateAmount());
        return donateResponseDTO;
    }

    // 기부 내역 단건 조회
    public UserDonationVO getDonation(Long userDonationId) {
        UserDonationVO userDonation = userDonationDAO.findById(userDonationId);
        if (userDonation == null) {
            throw new RuntimeException("해당 기부 내역이 존재하지 않습니다.");
        }

        return userDonation;
    }

    // 유저의 기부 내역 전체 조회
    public List<UserDonationVO> getAllDonations(Long userId) {
        MemberVO member = memberDAO.findById(userId);
        if (member == null) {
            throw new RuntimeException("해당 유저는 존재하지 않습니다.");
        }

        return userDonationDAO.findByUserId(userId);
    }

    // 기부 내역 수정
    public String updateDonation(Long userDonationId, DonateRequestDTO donateRequestDTO, Long userId) {
        MemberVO member = memberDAO.findById(userId);
        if (member.getRole() != Role.ROLE_ADMIN) {
            throw new RuntimeException("관리자만 접근 가능합니다.");
        }

        UserDonationVO userDonation = userDonationDAO.findById(userDonationId);
        if (userDonation == null) {
            throw new RuntimeException("해당 기부 내역이 존재하지 않습니다.");
        }

        userDonation.setDonationAmount(donateRequestDTO.getDonateAmount());
        userDonation.setAnonymous(donateRequestDTO.isAnonymous());

        userDonationDAO.updateUserDonation(userDonation);

        return "기부 내역이 수정되었습니다.";
    }

    // 기부 내역 삭제
    public String deleteDonation(Long userDonationId, Long userId) {
        MemberVO member = memberDAO.findById(userId);
        if (member == null) {
            throw new RuntimeException("해당 유저는 존재하지 않습니다.");
        }

        UserDonationVO userDonationVO = userDonationDAO.findById(userDonationId);
        if (userDonationVO == null) {
            throw new RuntimeException("해당 기부 내역이 존재하지 않습니다.");
        }

        if (!Objects.equals(userDonationVO.getUserId(), member.getUserId())) {
            throw new RuntimeException("해당 유저는 삭제 권한이 없습니다. (본인만 삭제 가능)");
        }

        userDonationDAO.deleteUserDonation(userDonationId);
        return "정상적으로 기부 내역이 삭제되었습니다.";
    }
    // 공통 검증 메서드
    private MemberVO validateMember(Long userId) {
        MemberVO member = memberDAO.findById(userId);
        if (member == null) {
            throw new RuntimeException("유효하지 않은 유저입니다.");
        }
        return member;
    }

    private FundVO validateFund(Long fundId) {
        FundVO fund = fundDAO.selectById(fundId);
        if (fund == null) {
            throw new RuntimeException("펀딩이 존재하지 않습니다.");
        }

        if (fund.getProgress() == ProgressType.End) {
            throw new RuntimeException("해당 기부는 종료되었습니다.");
        }
        return fund;
    }

    // 유저가 참여한 기부 전체 조회하기
    public List<UserDonationDetailDTO> findMyDonations(Long userId) {

        List<UserDonationDetailDTO> myDonations = userDonationDAO.findAllDonationDetailsByUserId(userId);

        if (myDonations == null || myDonations.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> productIds = myDonations.stream()
                .map(UserDonationDetailDTO::getProductId)
                .collect(Collectors.toList());

        List<S3ImageVO> allImages = s3ImageDAO.findImagesForPostIds(ImageType.Funding, productIds);

        Map<Long, List<S3ImageVO>> imagesByProductId = allImages.stream()
                .collect(Collectors.groupingBy(S3ImageVO::getPostId));
        myDonations.forEach(donation ->
                donation.setImages(imagesByProductId.getOrDefault(donation.getProductId(), Collections.emptyList()))
        );

        return myDonations;
    }

}
