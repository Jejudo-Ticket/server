package org.funding.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.funding.security.account.domain.MemberVO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
  private String username;
  private String email;
  private List<String> roles;


  public static UserInfoDTO of(MemberVO member) {
    return new UserInfoDTO(
            member.getUsername(),
            member.getEmail(),
            member.getAuthList().stream()
                    .map(a -> a.getAuth())  // 권한 리스트를 문자열로 변환
                    .toList()
    );
  }
}
