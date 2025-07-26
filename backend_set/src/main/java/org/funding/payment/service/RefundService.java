package org.funding.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefundService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getToken(String apiKey, String secretKey) {
        try {
            String url = "https://api.iamport.kr/users/getToken";
            
            Map<String, String> body = new HashMap<>();
            body.put("imp_key", apiKey);
            body.put("imp_secret", secretKey);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                Map<String, Object> responseData = (Map<String, Object>) responseBody.get("response");
                return (String) responseData.get("access_token");
            } else {
                throw new RuntimeException("토큰 발급 실패");
            }
        } catch (Exception e) {
            log.error("토큰 발급 중 오류 발생", e);
            throw new RuntimeException("토큰 발급 실패: " + e.getMessage());
        }
    }

    public void refundWithToken(String token, String impUid, String reason) {
        try {
            String url = "https://api.iamport.kr/payments/cancel";

            Map<String, Object> body = new HashMap<>();
            body.put("imp_uid", impUid);
            body.put("reason", reason);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("환불 성공: {}", impUid);
            } else {
                log.error("환불 실패: {}", response.getBody());
                throw new RuntimeException("환불 처리 실패");
            }
        } catch (Exception e) {
            log.error("환불 처리 중 오류 발생", e);
            throw new RuntimeException("환불 처리 실패: " + e.getMessage());
        }
    }
}