package org.funding.domain.votes.controller;

import lombok.RequiredArgsConstructor;
import org.funding.global.security.util.Auth;
import org.funding.domain.votes.dto.VotesRequestDTO;
import org.funding.domain.votes.dto.VotesResponseDTO;
import org.funding.domain.votes.service.VotesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VotesController {

    private final VotesService votesService;


    @Auth
    @GetMapping("")
    public ResponseEntity<Boolean> hasVoted(@RequestParam("projectId") Long projectId,
                                            HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");
        VotesRequestDTO requestDTO = new VotesRequestDTO();
        requestDTO.setProjectId(projectId);
        requestDTO.setUserId(userId);

        Boolean voted = votesService.hasVoted(requestDTO);

        return ResponseEntity.ok(voted);
    }

    @Auth
    @DeleteMapping("")
    public ResponseEntity<Void> cancelVote(@RequestBody Long projectId,
                                           HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        votesService.deleteVotes(projectId, userId);

        return ResponseEntity.noContent().build();
    }

    @Auth
    @GetMapping("/my-votes")
    public ResponseEntity<List<Long>> findVotedProjects(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Long> votedProjects = votesService.findVotedProjects(userId);

        return ResponseEntity.ok(votedProjects);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countVotes(@RequestParam("projectId") Long projectId) {

        Long voteCount = votesService.countVotes(projectId);

        return ResponseEntity.ok(voteCount);
    }


    @Auth
    @PostMapping("/{projectId}")
    public ResponseEntity<VotesResponseDTO> toggleVote(
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {

        Long userId = (Long) request.getAttribute("userId");

        VotesResponseDTO responseDTO = votesService.toggleVote(projectId, userId);

        if (responseDTO == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(responseDTO);
    }



}