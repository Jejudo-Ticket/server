package org.funding.mapping.userKeyword.service;

import lombok.RequiredArgsConstructor;
import org.funding.mapping.userKeyword.dao.UserKeywordDAO;
import org.funding.mapping.userKeyword.dto.UserKeywordRequestDTO;
import org.funding.mapping.userKeyword.vo.UserKeywordVO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserKeywordService {

    private final UserKeywordDAO userKeywordDAO;

    public void mapUserKeyword(UserKeywordRequestDTO requestDTO) {
        UserKeywordVO userKeywordVO = userKeywordDAO.selectUserKeyword(requestDTO);

        if (userKeywordVO != null) {
            // 이미 매핑되어 잇음
            return;
        }

        userKeywordDAO.insertUserKeyword(requestDTO);
    }

    public void unmapProjectKeyword(UserKeywordRequestDTO requestDTO) {
        UserKeywordVO userKeywordVO = userKeywordDAO.selectUserKeyword(requestDTO);

        if (userKeywordVO == null) {
            // 이미 매핑되어 있지 않음
            return;
        }

        userKeywordDAO.deleteUserKeyword(requestDTO);
    }
}
