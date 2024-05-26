package swe.issue.domain;

import java.util.Arrays;
import java.util.Objects;
import swe.issue.exception.IssueException;
import swe.issue.exception.IssueExceptionType;

public enum IssuePriority {

  BLOCKER("blocker"),
  CRITICAL("critical"),
  MAJOR("major"),
  MINOR("minor"),
  TRIVIAL("trivial");

  private final String name;

  IssuePriority(final String name) {
    this.name = name;
  }

  public static IssuePriority from(final String priorityName) {
    return Arrays.stream(IssuePriority.values())
        .filter(priority -> Objects.equals(priority.name, priorityName))
        .findAny()
        .orElseThrow(() -> new IssueException(IssueExceptionType.ISSUE_PRIORITY_NOT_FOUND));
  }

  public String getName() {
    return name;
  }
}
