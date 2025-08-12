package org.funding.domain.badge.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.funding.domain.badge.dto.BadgeResponseDTO;
import org.funding.domain.badge.vo.BadgeVO;
import org.funding.mapping.userBadge.vo.UserBadgeVO;

import java.util.List;

@Mapper
public interface BadgeDAO {

    // 뱃지 생성
    void insertBadge(BadgeVO badgeVO);

    // 뱃지 업데이트
    void updateBadge(BadgeVO badgeVO);

    // 뱃지 삭제
    void deleteBadge(Long id);

    // 뱃지 단건 조회
    BadgeResponseDTO selectBadgeById(Long badgeId);

    // 뱃지 전체 조회
    List<BadgeResponseDTO> selectAllBadges();


    // 뱃지 자동 부여 기능
    List<BadgeVO> selectAutoGrantBadges();
    boolean hasUserBadge(@Param("userId") Long userId, @Param("badgeId") Long badgeId);
    void insertUserBadge(UserBadgeVO userBadgeVO);


    // 뱃지 자동 부여 기능 (구체화)
    boolean hasCompletedFundedProject(Long userId);
    boolean isAdmin(Long userId);
    boolean hasDonated(Long userId);
    boolean hasJoinedChallenge(Long userId);
    boolean hasSubscribedFinancialProduct(Long userId);
    boolean hasProjectWithTenOrMoreComments(Long userId);
    boolean hasPostedTenComments(Long userId);
    boolean hasProjectWithTenOrMoreLikes(Long userId);
}
