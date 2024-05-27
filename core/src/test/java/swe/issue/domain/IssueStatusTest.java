package swe.issue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static swe.issue.exception.IssueExceptionType.ISSUE_CHANGE_STATUS_FORBIDDEN;
import static swe.user.domain.UserRole.PL;
import static swe.user.domain.UserRole.TESTER;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import swe.issue.exception.IssueException;
import swe.user.domain.User;

class IssueStatusTest {

  @Test
  void 이름으로_IssueStatus_객체를_반환받는다() {
    final String issueStatus = "new";
    final IssueStatus expected = IssueStatus.NEW;

    final IssueStatus actual = IssueStatus.from(issueStatus);

    assertThat(actual)
        .isEqualTo(expected);
  }

  @Nested
  class Status를_바꿀_떄_적절한_권한이어야한다 {

    @Test
    void 적절하지않은_유저권한일_경우_예외처리한다() {
      final User tester = new User("accountId", "password", "nickName", TESTER);
      final IssueStatus closed = IssueStatus.CLOSED;

      assertThatThrownBy(() -> closed.validateUserRoleUpdateStatus(tester))
          .isInstanceOf(IssueException.class)
          .hasMessage(ISSUE_CHANGE_STATUS_FORBIDDEN.getMessage());
    }

    @Test
    void 적절한_유저권한일_경우_통과시킨다() {
      final User pl = new User("accountId", "password", "nickName", PL);
      final IssueStatus closed = IssueStatus.CLOSED;

      closed.validateUserRoleUpdateStatus(pl);
    }
  }
}