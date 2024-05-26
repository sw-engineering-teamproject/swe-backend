package swe.dto;

import java.util.List;
import swe.issue.domain.IssueStatus;

public record IssueStatusNameResponse(String statusName) {

  public static List<IssueStatusNameResponse> createList(final List<IssueStatus> issueStatuses) {
    return issueStatuses.stream()
        .map(IssueStatus::getName)
        .map(IssueStatusNameResponse::new)
        .toList();
  }
}
