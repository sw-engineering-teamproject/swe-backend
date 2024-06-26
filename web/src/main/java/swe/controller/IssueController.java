package swe.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swe.dto.issue.CommentAddRequest;
import swe.dto.issue.IssueAssignRequest;
import swe.dto.issue.IssueCreateResponse;
import swe.dto.issue.IssueDescriptionUpdateRequest;
import swe.dto.issue.IssueDetailResponse;
import swe.dto.issue.IssuePriorityNameResponse;
import swe.dto.issue.IssuePriorityUpdateRequest;
import swe.dto.issue.IssueReportedResponse;
import swe.dto.issue.IssueResponse;
import swe.dto.issue.IssueStatusNameResponse;
import swe.dto.issue.IssueStatusUpdateRequest;
import swe.dto.user.UserResponse;
import swe.issue.application.IssueService;
import swe.issue.dto.IssueCreateRequest;
import swe.user.dto.JwtMemberId;

@RequiredArgsConstructor
@RestController
public class IssueController {

  private final IssueService issueService;

  @PostMapping("/issues")
  public ResponseEntity<IssueCreateResponse> createIssue(
      final JwtMemberId jwtMemberId, @RequestBody final IssueCreateRequest request
  ) {
    final Long issueId = issueService.createIssue(jwtMemberId.userId(), request);
    return ResponseEntity.status(HttpStatus.CREATED).body(new IssueCreateResponse(issueId));
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

  @PostMapping("/issues/{issueId}/comments")
  public ResponseEntity<Void> comment(
      @PathVariable final Long issueId, final JwtMemberId jwtMemberId,
      @RequestBody final CommentAddRequest commentAddRequest
  ) {
    issueService.commentContent(jwtMemberId.userId(), issueId, commentAddRequest.content());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/issues/{issueId}/description")
  public ResponseEntity<Void> updateDescription(
      @PathVariable final Long issueId,
      @RequestBody final IssueDescriptionUpdateRequest issueDescriptionUpdateRequest
  ) {
    issueService.updateDescription(issueId, issueDescriptionUpdateRequest.newDescription());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/issues/{issueId}/status")
  public ResponseEntity<Void> updateStatus(
      @PathVariable final Long issueId, final JwtMemberId jwtMemberId,
      @RequestBody final IssueStatusUpdateRequest issueStatusUpdateRequest
  ) {
    issueService.updateStatus(jwtMemberId.userId(), issueId, issueStatusUpdateRequest.status());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/issues/{issueId}/priority")
  public ResponseEntity<Void> updatePriority(
      @PathVariable final Long issueId,
      @RequestBody final IssuePriorityUpdateRequest issuePriorityUpdateRequest
  ) {
    issueService.updatePriority(issueId, issuePriorityUpdateRequest.priority());
    return ResponseEntity.ok().build();
  }

  @GetMapping("/issues/priority")
  public ResponseEntity<List<IssuePriorityNameResponse>> getPriorityList() {
    final var issuePriorities = issueService.getIssuePriority();
    return ResponseEntity.ok(IssuePriorityNameResponse.createList(issuePriorities));
  }

  @GetMapping("/Issues/{issueId}/recommend")
  public ResponseEntity<List<UserResponse>> recommendAssignee(@PathVariable final Long issueId) {
    final var users = issueService.recommendIssue(issueId);
    return ResponseEntity.ok(UserResponse.createList(users));
  }

  @GetMapping("/issues/status")
  public ResponseEntity<List<IssueStatusNameResponse>> getStatusList() {
    final var issueStatuses = issueService.getIssueStatuses();
    return ResponseEntity.ok(IssueStatusNameResponse.createList(issueStatuses));
  }

  @PostMapping("/issues/{issueId}/assignee")
  public ResponseEntity<Void> assignUser(
      @PathVariable final Long issueId, @RequestBody final IssueAssignRequest issueAssignRequest,
      final JwtMemberId jwtMemberId
  ) {
    issueService.assignUser(jwtMemberId.userId(), issueId, issueAssignRequest.assigneeId());
    return ResponseEntity.ok().build();
  }

  @GetMapping("/issues/{issueId}")
  public ResponseEntity<IssueDetailResponse> getIssueDetail(@PathVariable final Long issueId) {
    final var issue = issueService.findIssueDetail(issueId);
    return ResponseEntity.ok(IssueDetailResponse.from(issue));
  }

  @GetMapping("/projects/{projectId}/statistics/day")
  public ResponseEntity<List<IssueReportedResponse>> viewDailyStatistics(
      @PathVariable final Long projectId
  ) {
    final var statistics = issueService.getIssueCreateCountByDay(projectId);
    return ResponseEntity.ok(IssueReportedResponse.createListByLocalDate(statistics));
  }

  @GetMapping("/projects/{projectId}/statistics/month")
  public ResponseEntity<List<IssueReportedResponse>> viewMonthlyStatistics(
      @PathVariable final Long projectId
  ) {
    final var statistics = issueService.getIssueCreateCountByMonth(projectId);
    return ResponseEntity.ok(IssueReportedResponse.createListByLocalDate(statistics));
  }

  @GetMapping("/projects/{projectId}/statistics/status")
  public ResponseEntity<List<IssueReportedResponse>> viewStatusStatistics(
      @PathVariable final Long projectId
  ) {
    final var statistics = issueService.getStatusCount(projectId);
    return ResponseEntity.ok(IssueReportedResponse.createListByStatus(statistics));
  }

  @GetMapping("/projects/{projectId}/statistics/priority")
  public ResponseEntity<List<IssueReportedResponse>> viewPriorityStatistics(
      @PathVariable final Long projectId
  ) {
    final var statistics = issueService.getPriorityCount(projectId);
    return ResponseEntity.ok(IssueReportedResponse.createListByPriority(statistics));
  }

  @GetMapping("/projects/{projectId}/statistics/assignee")
  public ResponseEntity<List<IssueReportedResponse>> viewAssigneeStatistics(
      @PathVariable final Long projectId
  ) {
    final var statistics = issueService.getAssigneeCount(projectId);
    return ResponseEntity.ok(IssueReportedResponse.createListByUser(statistics));
  }

  @GetMapping("/projects/{projectId}/statistics/reporter")
  public ResponseEntity<List<IssueReportedResponse>> viewReporterStatistics(
      @PathVariable final Long projectId
  ) {
    final var statistics = issueService.getReporterCount(projectId);
    return ResponseEntity.ok(IssueReportedResponse.createListByUser(statistics));
  }
}
