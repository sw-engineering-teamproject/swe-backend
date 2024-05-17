package swe.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swe.dto.ProjectCreateRequest;
import swe.project.application.ProjectService;
import swe.user.dto.JwtMemberId;

@RestController
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  @PostMapping("/projects")
  public ResponseEntity<Void> createProject(
      @RequestBody final ProjectCreateRequest request, final JwtMemberId jwtMemberId
  ) {
    final Long projectId = projectService.createProject(request.title(), jwtMemberId.memberId());
    return ResponseEntity.created(URI.create("/projects/" + projectId)).build();
  }
}
