package swe.issue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static swe.fixture.IssueFixture.id가_없는_Issue;
import static swe.fixture.UserFixture.id가_있는_유저;
import static swe.issue.domain.IssueFilterCondition.REPORTER;

import org.junit.jupiter.api.Test;
import swe.user.domain.User;

class IssueFilterConditionTest {

  @Test
  void 이슈를_검사하는_기능() {
    final User reporter = id가_있는_유저();
    final Issue issue = id가_없는_Issue(reporter, 10L);

    final boolean actual = REPORTER.filterIssue(issue, reporter.getId().toString());

    assertThat(actual)
        .isTrue();
  }
}