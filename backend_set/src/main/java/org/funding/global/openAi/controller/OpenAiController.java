package org.funding.global.openAi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.funding.domain.fund.vo.FundVO;
import org.funding.global.openAi.dto.ChatRequestDTO;
import org.funding.global.openAi.dto.ChatResponseDTO;
import org.funding.global.openAi.dto.SummaryFundRequestDTO;
import org.funding.global.openAi.dto.SummaryFundResponseDTO;
import org.funding.global.openAi.service.ChatService;
import org.funding.global.openAi.service.FundAIService;
import org.funding.global.openAi.service.OpenVisionService;
import org.funding.global.security.util.Auth;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@Log4j2
public class OpenAiController {

    private final ChatService chatService;
    private final FundAIService fundAIService;
    private final OpenVisionService openVisionService;

    @Auth
    @PostMapping(value = "/ask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChatResponseDTO ask(@RequestBody ChatRequestDTO chatRequestDTO,
                               HttpServletRequest request) {
        return chatService.ask(chatRequestDTO);
    }

    // 펀딩 ai 요약하기
    @Auth
    @PostMapping(value = "/fund", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SummaryFundResponseDTO summaryFund(@RequestBody SummaryFundRequestDTO summaryFundRequestDTO,
                                              HttpServletRequest request) {
        return chatService.summaryFund(summaryFundRequestDTO);
    }

    // 현재 펀딩과 비슷한 펀딩 추천받기
    @Auth
    @GetMapping(value = "/{fundId}/ai-recommend")
    public List<FundVO> recommendSimilarFundsByAI(@PathVariable Long fundId,
                                                  HttpServletRequest request) {
        return fundAIService.aiSimilar(fundId);
    }

    // 이미지 분석
    @Auth
    @PostMapping("/analyze-image")
    public ResponseEntity<String> analyzeImage(@RequestParam("imageUrl") String imageUrl,
                                               @RequestParam("prompt") String prompt,
                                               HttpServletRequest request) {
        String result = openVisionService.analyzeImage(imageUrl, prompt);
        return ResponseEntity.ok(result);
    }

}
