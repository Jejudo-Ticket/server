package org.funding.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.funding.security.util.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
          HttpServletRequest request,
          HttpServletResponse response,
          AccessDeniedException accessDeniedException
  ) throws IOException, ServletException {

    log.error("========== 인가 에러 ============");
    JsonResponse.sendError(
            response,
            HttpStatus.FORBIDDEN,
            "권한이 부족합니다."
    );
  }
}