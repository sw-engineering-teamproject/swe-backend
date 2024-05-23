package swe.issue.domain;

import static swe.issue.exception.IssueExceptionType.ISSUE_STATUS_NOT_FOUND;

import java.util.Arrays;
import java.util.Objects;
import swe.issue.exception.IssueException;

public enum IssueStatus {

  NEW("new"),
  ASSIGNED("assigned"),
  RESOLVED("resolved"),
  CLOSED("closed"),
  REOPENED("reopened");

  private final String value;

  IssueStatus(final String value) {
    this.value = value;
  }

  public static IssueStatus from(final String value) {
    return Arrays.stream(values())
        .filter(condition -> Objects.equals(condition.value, value))
        .findAny()
        .orElseThrow(() -> new IssueException(ISSUE_STATUS_NOT_FOUND));
  }
}
