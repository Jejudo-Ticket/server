package org.funding.fund.controller;

import lombok.RequiredArgsConstructor;
import org.funding.fund.dto.FundCreateDTO;
import org.funding.fund.service.FundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fund")
@RequiredArgsConstructor
public class FundController {

    private final FundService fundService;

    //summary = "백엔드 개발용 펀딩 상품 입력 폼 조회(프론트 연결X)",
    //description = "선택한 프로젝트 ID와 상품 타입에 따라 해당하는 펀딩 상품 입력 폼 데이터를 반환합니다."
    @GetMapping("/loadForm")
    public ResponseEntity<?> loadFundForm(@RequestParam Long projectId, @RequestParam String productType) {
        Object formDTO = fundService.getFormByProductType(productType, projectId);

        if (formDTO == null) {
            return ResponseEntity.badRequest().body("Unsupported productType: " + productType);
        }
        return ResponseEntity.ok(formDTO);
    }

    // summary = "펀딩 상품 개설을 위한 프로젝트 정보 조회",
    // description = "프로젝트 ID에 해당하는 프로젝트의 기본 정보(제목, 기간 등)를 반환합니다.
    @GetMapping("/form/{projectId}")
    public ResponseEntity<?> getFundCreateForm(@PathVariable Long projectId) {
        FundCreateDTO.ProjectInfoResponseDTO response = fundService.getProjectInfoForFunding(projectId);
        
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    //summary = "펀딩 상품 개설",
    //description = "요청된 정보를 기반으로 새로운 펀딩 상품을 생성합니다. 성공 시 생성된 펀딩의 ID와 메시지를 반환합니다."
    @PostMapping("/create")
    public ResponseEntity<?> createFund(@RequestBody FundCreateDTO.FundCreateRequestDTO requestDTO) {
        try {
            FundCreateDTO.FundCreatedResponseDTO response = fundService.createFund(requestDTO);
            return ResponseEntity.ok("펀딩 생성 성공");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fund creation failed: " + e.getMessage());
        }
    }
}
