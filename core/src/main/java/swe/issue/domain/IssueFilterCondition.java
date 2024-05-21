package swe.issue.domain;

import static swe.issue.exception.IssueExceptionType.ISSUE_FILTER_CONDITION_NOT_FOUND;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;
import swe.issue.exception.IssueException;

public enum IssueFilterCondition {

  ASSIGNEE("assignee",
      (issue, value) -> issue.isEqualAssigneeId(Long.valueOf(value))
  ),
  REPORTER("reporter",
      (issue, value) -> issue.isEqualReporterId(Long.valueOf(value))
  ),
  ISSUE_STATUS("issueStatus",
      (issue, status) -> issue.isEqualStatus(IssueStatus.valueOf(status))
  );

  private final String value;
  private final BiFunction<Issue, String, Boolean> filterFunction;

  IssueFilterCondition(
      final String value, final BiFunction<Issue, String, Boolean> filterFunction
  ) {
    this.value = value;
    this.filterFunction = filterFunction;
  }

  public static IssueFilterCondition from(final String value) {
    return Arrays.stream(values())
        .filter(condition -> Objects.equals(condition.value, value))
        .findAny()
        .orElseThrow(() -> new IssueException(ISSUE_FILTER_CONDITION_NOT_FOUND));
  }

  public boolean filterIssue(final Issue issue, final String conditionValue) {
    return filterFunction.apply(issue, conditionValue);
  }
}
