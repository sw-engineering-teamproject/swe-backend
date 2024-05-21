package swe.issue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static swe.issue.domain.Comment.createInitialProjectComment;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import swe.user.domain.User;
import swe.user.domain.UserRole;

class IssueTest {

  @Test
  void 이슈를_생성하면_comment도_등록된다() {
    //given
    final User reporter
        = new User(1L, "accountId", "password", "nickName", UserRole.PL);

    //when
    final Issue actual = Issue.createIssue("title", "description", 10L, reporter);

    //then
    final Issue expected = new Issue("title", "description", 10L, reporter);
    final Comment expectedComment
        = createInitialProjectComment(expected, reporter.getId(), reporter.getNickname());

    assertAll(
        () -> assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("comments", "reportedDate")
            .isEqualTo(expected),
        () -> assertThat(actual.getComments())
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("createdAt", "issue")
            .containsExactlyInAnyOrder(expectedComment)
    );
  }

  @Test
  void 이슈의_reporter의_Id가_동일한지_파악한다() {
    //given
    final User reporter
        = new User(1L, "accountId", "password", "nickName", UserRole.PL);
    final Issue actual = Issue.createIssue("title", "description", 10L, reporter);

    //when, then
    assertAll(
        () -> assertThat(actual.isEqualReporterId(1L))
            .isTrue(),
        () -> assertThat(actual.isEqualAssigneeId(3L))
            .isFalse()
    );
  }

  @Nested
  class 이슈의_assignee의_Id가_동일한지_파악한다 {

    @Test
    void 이슈의_assignee가_있는경우_id를_비교한다() {
      //given
      final User reporter
          = new User(1L, "accountId", "password", "nickName", UserRole.PL);
      final Issue actual = Issue.createIssue("title", "description", 10L, reporter);
      actual.assignAssignee(reporter);

      //when, then
      assertAll(
          () -> assertThat(actual.isEqualAssigneeId(1L))
              .isTrue(),
          () -> assertThat(actual.isEqualAssigneeId(3L))
              .isFalse()
      );
    }

    @Test
    void 이슈의_assignee가_없는경우_false를_반환한다() {
      //given
      final User reporter
          = new User(1L, "accountId", "password", "nickName", UserRole.PL);
      final Issue actual = Issue.createIssue("title", "description", 10L, reporter);

      //when, then
      assertAll(
          () -> assertThat(actual.isEqualAssigneeId(1L))
              .isFalse(),
          () -> assertThat(actual.isEqualAssigneeId(3L))
              .isFalse()
      );
    }
  }

  @Test
  void 이슈의_상태가_동일한지_파악한다() {
    //given
    final User reporter
        = new User(1L, "accountId", "password", "nickName", UserRole.PL);
    final Issue actual = Issue.createIssue("title", "description", 10L, reporter);

    //when, then
    assertAll(
        () -> assertThat(actual.isEqualStatus(IssueStatus.NEW))
            .isTrue(),
        () -> assertThat(actual.isEqualStatus(IssueStatus.CLOSED))
            .isFalse()
    );
  }
}