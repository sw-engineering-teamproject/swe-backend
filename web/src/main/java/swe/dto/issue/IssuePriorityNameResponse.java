package swe.dto.issue;

import java.util.List;
import swe.issue.domain.IssuePriority;

public record IssuePriorityNameResponse(String priorityName) {

  public static List<IssuePriorityNameResponse> createList(
      final List<IssuePriority> issuePriorities
  ) {
    return issuePriorities.stream()
        .map(IssuePriority::getName)
        .map(IssuePriorityNameResponse::new)
        .toList();
  }
}
