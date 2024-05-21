package swe.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swe.dto.IssueResponse;
import swe.issue.application.IssueService;
import swe.issue.dto.IssueCreateRequest;
import swe.user.dto.JwtMemberId;

@RequiredArgsConstructor
@RestController
public class IssueController {

  private final IssueService issueService;

  @PostMapping("/issues")
  public ResponseEntity<Void> createIssue(
      final JwtMemberId jwtMemberId, @RequestBody final IssueCreateRequest request
  ) {
    final Long issueId = issueService.createIssue(jwtMemberId.memberId(), request);
    return ResponseEntity.created(URI.create("/issues/" + issueId)).build();
  }

  @GetMapping("/issues")
  public ResponseEntity<List<IssueResponse>> findProjectsIssues(
      @RequestParam final Long projectId
  ) {
    final var issues = issueService.findIssues(projectId);
    final List<IssueResponse> responses = IssueResponse.createList(issues);
    return ResponseEntity.ok(responses);
  }

  @GetMapping("/issues/filter")
  public ResponseEntity<List<IssueResponse>> filterProjectIssue(
      @RequestParam final Long projectId, @RequestParam final String condition,
      @RequestParam final String conditionValue
  ) {
    final var issues = issueService.filterIssues(projectId, condition, conditionValue);
    final List<IssueResponse> responses = IssueResponse.createList(issues);
    return ResponseEntity.ok(responses);
  }
}
