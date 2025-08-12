package org.funding.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.funding.global.security.util.JwtProcessor;
import org.funding.domain.user.dto.MemberLoginDTO;
import org.funding.domain.user.dto.MemberLoginResponseDTO;
import org.funding.domain.user.dto.MemberSignupDTO;
import org.funding.domain.user.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtProcessor jwtProcessor;

    @PostMapping(value = "/signup", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> signup(@RequestBody MemberSignupDTO signupDTO) {
        memberService.signup(signupDTO);
        return ResponseEntity.ok("회원 가입 성공");
    }

    @PostMapping(value = "/login", produces = "application/json; charset=UTF-8")
    public ResponseEntity<MemberLoginResponseDTO> login(@RequestBody MemberLoginDTO loginDTO) {
        MemberLoginResponseDTO response = memberService.login(loginDTO.getEmail(), loginDTO.getPassword());
        return ResponseEntity.ok(response);
    }

    // 테스트용 토큰 생성
    @PostMapping(value = "/test/token/generate")
    public ResponseEntity<String> generateToken() {
        // 테스트용 하드코딩 사용자 정보
        String testUsername = "testUser";
        String testRole = "ROLE_NORMAL";

        String jwt = jwtProcessor.generateTokenWithRole(testUsername, testRole);

        return ResponseEntity.ok(jwt);
    }
}
