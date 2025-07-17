package org.funding.fund.controller;

import lombok.RequiredArgsConstructor;
import org.funding.fund.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fund")
@RequiredArgsConstructor
public class FundController {

    private final FundService fundService;

//    @PostMapping("/create")
//    public String createFund(@RequestBody FundDTO.createFund() dto) {
//        fundService.createFund(dto);
//        return ResponseEntity.ok("펀딩 생성 성공");
//    }
}
