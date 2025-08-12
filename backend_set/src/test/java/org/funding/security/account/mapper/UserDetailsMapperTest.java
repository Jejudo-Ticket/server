//package org.funding.security.account.mapper;
//
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.funding.global.config.RootConfig;
//import org.funding.global.config.security.SecurityConfig;
//import org.funding.global.domain.account.security.AuthVO;
//import org.funding.global.domain.account.security.MemberVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = { RootConfig.class, SecurityConfig.class })
//@Log4j2
//public class UserDetailsMapperTest {
//
//  @Autowired
//  private UserDetailsMapper mapper;
//
//  @Test
//  public void get() {
//    // admin 사용자 정보 조회
//    MemberVO member = mapper.get("admin");
//    log.info("사용자 정보: {}", member);
//
//    // 권한 목록 출력
//    log.info("권한 개수: {}", member.getAuthList().size());
//    for(AuthVO auth : member.getAuthList()) {
//      log.info("권한: {}", auth);
//    }
//  }
//}