package org.funding.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.funding.payment.dto.FundJoinDto;
import org.funding.payment.service.PaymentService;
import org.funding.payment.service.RefundService;
import org.funding.security.account.domain.CustomUser;
import org.springframework.security.core.Authentication;
import org.funding.user.dao.MemberDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final RefundService refundService;
    private final MemberDAO memberDAO;
 
    @Value("${imp.api.key}")
    private String apiKey;
 
    @Value("${imp.api.secretkey}")
    private String secretKey;
 
    @PostMapping("/fund/join/payment")
    public ResponseEntity<String> paymentComplete(Authentication authentication, @RequestBody List<FundJoinDto> fundJoinDtos) throws IOException {
        String joinNumber = String.valueOf(fundJoinDtos.get(0).getJoinNumber());
        try {
            CustomUser customUser = (CustomUser) authentication.getPrincipal();
            String username = customUser.getUsername();
            
            // username(이메일)로 회원 정보 조회
            Long userId = memberDAO.findByEmail(username).getUserId();
            paymentService.saveFundJoin(userId, fundJoinDtos);
            
            log.info("결제 성공 : 가입 번호 {}", joinNumber);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.info("펀드 가입 환불 진행 : 가입 번호 {}", joinNumber);
            String token = refundService.getToken(apiKey, secretKey);
            refundService.refundWithToken(token, joinNumber, e.getMessage());
            return ResponseEntity.badRequest().body("펀드 가입 처리 중 오류가 발생했습니다. 환불 처리되었습니다.");
        }
    }
}