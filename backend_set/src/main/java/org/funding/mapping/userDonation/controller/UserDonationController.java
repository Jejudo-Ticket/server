package org.funding.mapping.userDonation.controller;

import lombok.RequiredArgsConstructor;
import org.funding.global.security.util.Auth;
import org.funding.mapping.userDonation.dto.DonateRequestDTO;
import org.funding.mapping.userDonation.dto.DonateResponseDTO;
import org.funding.mapping.userDonation.dto.UserDonationDetailDTO;
import org.funding.mapping.userDonation.service.UserDonationService;
import org.funding.mapping.userDonation.vo.UserDonationVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user-donation")
@RequiredArgsConstructor
public class UserDonationController {

    private final UserDonationService userDonationService;
    // 기부
    @Auth
    @PostMapping("/saving")
    public ResponseEntity<DonateResponseDTO> donate(@RequestBody DonateRequestDTO donateRequestDTO,
                                                    HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userDonationService.donate(donateRequestDTO, userId));
    }


    // 기부 내역 상세 조회
    @Auth
    @GetMapping("/donation/{id}")
    public ResponseEntity<UserDonationVO> getDonation(@PathVariable("id") Long id,
                                                      HttpServletRequest request) {
        return ResponseEntity.ok(userDonationService.getDonation(id));
    }

    // 유저의 기부 내역 전체 조회
    @Auth
    @GetMapping("/donation-history")
    public ResponseEntity<List<UserDonationVO>> getAllDonations(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userDonationService.getAllDonations(userId));
    }

    // 기부 내역 수정 (관리자용)
    @Auth
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateDonation(@PathVariable("id") Long id,
                                                 @RequestBody DonateRequestDTO donateRequestDTO,
                                                 HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userDonationService.updateDonation(id, donateRequestDTO, userId));
    }

    // 기부 내역 삭제
    @Auth
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDonation(@PathVariable("id") Long id,
                                                 HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userDonationService.deleteDonation(id, userId));
    }

    // 내가 참여한 기부 내역 조회
    @Auth
    @GetMapping("/user/all/v2")
    public ResponseEntity<?> getMyAllDonations(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("인증 정보가 유효하지 않습니다.");
        }

        List<UserDonationDetailDTO> myDonations = userDonationService.findMyDonations(userId);

        return ResponseEntity.ok(myDonations);
    }
}
