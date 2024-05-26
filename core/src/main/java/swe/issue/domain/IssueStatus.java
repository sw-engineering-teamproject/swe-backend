package swe.issue.domain;

import static swe.issue.exception.IssueExceptionType.ISSUE_STATUS_NOT_FOUND;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import swe.issue.exception.IssueException;

@Getter
public enum IssueStatus {

  NEW("new"),
  ASSIGNED("assigned"),
  RESOLVED("resolved"),
  CLOSED("closed"),
  REOPENED("reopened");

  private final String name;

  IssueStatus(final String name) {
    this.name = name;
  }

  public static IssueStatus from(final String issueStatusName) {
    return Arrays.stream(values())
        .filter(status -> Objects.equals(status.name, issueStatusName))
        .findAny()
        .orElseThrow(() -> new IssueException(ISSUE_STATUS_NOT_FOUND));
  }
}
