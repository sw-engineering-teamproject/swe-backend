package swe.dto.issue;

import java.util.List;
import swe.issue.domain.Issue;
import swe.user.domain.User;

public record IssueResponse(
    Long id, String title, String reporterName, String assigneeName, String issueStatus
) {

  public static IssueResponse from(final Issue issue) {
    return new IssueResponse(
        issue.getId(),
        issue.getTitle(),
        issue.getReporter().getNickname(),
        issue.getAssignee().map(User::getNickname).orElse(null),
        issue.getStatus().name()
    );
  }

  public static List<IssueResponse> createList(final List<Issue> issues) {
    return issues.stream()
        .map(IssueResponse::from)
        .toList();
  }
}
