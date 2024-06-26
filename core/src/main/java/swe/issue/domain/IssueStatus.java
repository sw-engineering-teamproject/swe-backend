package swe.issue.domain;

import static java.util.Arrays.stream;
import static swe.issue.exception.IssueExceptionType.ISSUE_CHANGE_STATUS_FORBIDDEN;
import static swe.issue.exception.IssueExceptionType.ISSUE_STATUS_NOT_FOUND;
import static swe.user.domain.UserRole.ADMIN;
import static swe.user.domain.UserRole.DEV;
import static swe.user.domain.UserRole.PL;
import static swe.user.domain.UserRole.TESTER;

import java.util.List;
import java.util.Objects;
import lombok.Getter;
import swe.issue.exception.IssueException;
import swe.user.domain.User;
import swe.user.domain.UserRole;

@Getter
public enum IssueStatus {

  NEW("new", stream(UserRole.values()).toList()),
  ASSIGNED("assigned", List.of(PL, ADMIN)),
  RESOLVED("resolved", List.of(TESTER, ADMIN)),
  CLOSED("closed", List.of(PL, ADMIN)),
  FIXED("fixed", List.of(DEV, ADMIN)),
  REOPENED("reopened", List.of(PL, ADMIN));

  private final String name;
  private final List<UserRole> authorizeUserRoleUpdateStatus;

  IssueStatus(final String name, final List<UserRole> authorizeUserRoleUpdateStatus) {
    this.name = name;
    this.authorizeUserRoleUpdateStatus = authorizeUserRoleUpdateStatus;
  }

  public void validateUserRoleUpdateStatus(final User user) {
    if (!authorizeUserRoleUpdateStatus.contains(user.getUserRole())) {
      throw new IssueException(ISSUE_CHANGE_STATUS_FORBIDDEN);
    }
  }

  public static IssueStatus from(final String issueStatusName) {
    return stream(values())
        .filter(status -> Objects.equals(status.name, issueStatusName))
        .findAny()
        .orElseThrow(() -> new IssueException(ISSUE_STATUS_NOT_FOUND));
  }
}
