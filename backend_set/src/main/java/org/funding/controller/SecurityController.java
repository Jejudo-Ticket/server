package org.funding.controller;

import lombok.extern.slf4j.Slf4j;
import org.funding.security.account.domain.CustomUser;
import org.funding.security.account.domain.MemberVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/security")
@RestController
public class SecurityController {

  @GetMapping("/all") //  /api/security/all
  public ResponseEntity<String> doAll() {
    log.info("do all can access everybody");
    return ResponseEntity.ok("All can access everybody");
  }

  @GetMapping("/member") // /api/security/member
  public ResponseEntity<String> doMember(Authentication authentication) {

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    log.info("username = " + userDetails.getUsername());
    return ResponseEntity.ok(userDetails.getUsername());
  }

//  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/admin") //  /api/security/admin
  public ResponseEntity<MemberVO> doAdmin(
          @AuthenticationPrincipal CustomUser customUser
  ) {
    MemberVO member = customUser.getMember();
    log.info("username = " + member);
    return ResponseEntity.ok(member);
  }
}