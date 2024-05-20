package swe.issue.dto;

import swe.issue.domain.Issue;
import swe.user.domain.User;

public record IssueCreateRequest(
    String title,
    String description,
    Long projectId
) {

  public Issue toIssue(final User reporter) {
    return Issue.createIssue(title, description, projectId, reporter);
  }
}
