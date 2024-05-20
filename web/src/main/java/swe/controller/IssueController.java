package swe.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
}
