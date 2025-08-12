package org.funding.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSignupDTO {
    private String email;
    private String password;
    private String username;
    private String nickname;
    private String phoneNumber;

    private List<Long> keywordIds;

}
