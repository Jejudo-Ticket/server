package org.funding.project.controller;

import lombok.RequiredArgsConstructor;
import org.funding.project.dto.ProjectDTO;
import org.funding.project.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {
    
    private final ProjectService projectService;
    
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectWithFinancialProduct(@PathVariable Long projectId) {
        ProjectDTO projectDTO = projectService.getProjectWithFinancialProduct(projectId);
        
        if (projectDTO == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(projectDTO);
    }
}