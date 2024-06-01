package swe.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swe.dto.project.ProjectCreateRequest;
import swe.project.application.ProjectService;
import swe.project.dto.ProjectOverviewResponse;
import swe.user.dto.JwtMemberId;

@RestController
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  @PostMapping("/projects")
  public ResponseEntity<Void> createProject(
      @RequestBody final ProjectCreateRequest request, final JwtMemberId jwtMemberId
  ) {
    final Long projectId = projectService.createProject(request.title(), jwtMemberId.userId());
    return ResponseEntity.created(URI.create("/projects/" + projectId)).build();
  }

  @GetMapping("/projects")
  public ResponseEntity<List<ProjectOverviewResponse>> getProjectList() {
    final List<ProjectOverviewResponse> responses = projectService.readAllProject();
    return ResponseEntity.ok(responses);
  }
}
