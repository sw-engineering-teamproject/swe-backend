package swe.issue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static swe.issue.domain.Comment.createInitialProjectComment;

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
}