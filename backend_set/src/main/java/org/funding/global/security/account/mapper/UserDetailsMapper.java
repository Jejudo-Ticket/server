package org.funding.global.security.account.mapper;

import org.funding.global.security.account.domain.MemberVO;

public interface UserDetailsMapper {

  public MemberVO get(String username);
}
