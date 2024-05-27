package swe.issue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static swe.fixture.IssueFixture.id가_없는_Issue;
import static swe.fixture.UserFixture.id가_있는_유저;
import static swe.issue.domain.Comment.createInitialProjectComment;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import swe.user.domain.User;

class IssueTest {

  @Test
  void 이슈를_생성하면_comment도_등록된다() {
    //given
    final User reporter = id가_있는_유저();

    //when
    final Issue actual = Issue.createIssue("title", "description", 10L, reporter);

    //then
    final Issue expected = new Issue("title", "description", 10L, reporter, LocalDateTime.now());
    final Comment expectedComment
        = createInitialProjectComment(expected, reporter, reporter.getNickname());

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
    final User reporter = id가_있는_유저();
    final Issue actual = id가_없는_Issue(reporter, 10L);

    //when, then
    assertAll(
        () -> assertThat(actual.isEqualReporterId(reporter.getId()))
            .isTrue(),
        () -> assertThat(actual.isEqualAssigneeId(reporter.getId() + 1))
            .isFalse()
    );
  }

  @Nested
  class 이슈의_assignee의_Id가_동일한지_파악한다 {

    @Test
    void 이슈의_assignee가_있는경우_id를_비교한다() {
      //given
      final User reporter = id가_있는_유저();
      final Issue actual = id가_없는_Issue(reporter, 10L);
      actual.assignAssignee(reporter);

      //when, then
      assertAll(
          () -> assertThat(actual.isEqualAssigneeId(reporter.getId()))
              .isTrue(),
          () -> assertThat(actual.isEqualAssigneeId(reporter.getId() + 1))
              .isFalse()
      );
    }

    @Test
    void 이슈의_assignee가_없는경우_false를_반환한다() {
      //given
      final User reporter = id가_있는_유저();
      final Issue actual = id가_없는_Issue(reporter, 10L);

      //when, then
      assertAll(
          () -> assertThat(actual.isEqualAssigneeId(reporter.getId()))
              .isFalse(),
          () -> assertThat(actual.isEqualAssigneeId(reporter.getId() + 1))
              .isFalse()
      );
    }
  }

  @Test
  void 이슈의_상태가_동일한지_파악한다() {
    //given
    final User reporter = id가_있는_유저();
    final Issue actual = id가_없는_Issue(reporter, 10L);

    //when, then
    assertAll(
        () -> assertThat(actual.isEqualStatus(IssueStatus.NEW))
            .isTrue(),
        () -> assertThat(actual.isEqualStatus(IssueStatus.CLOSED))
            .isFalse()
    );
  }

  @Test
  void 이슈에_댓글을_추가한다() {
    //given
    final User reporter = id가_있는_유저();
    final Issue issue = id가_없는_Issue(reporter, 10L);

    final String content = "댓글 내용";

    //when
    issue.addComment(reporter, content);

    //then
    final Comment expcetedComment = new Comment(issue, reporter, content);
    assertThat(issue.getComments())
        .usingRecursiveFieldByFieldElementComparator()
        .contains(expcetedComment);
  }
}