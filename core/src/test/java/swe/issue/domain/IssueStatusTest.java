package swe.issue.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class IssueStatusTest {

  @Test
  void 이름으로_IssueStatus_객체를_반환받는다() {
    final String issueStatus = "new";
    final IssueStatus expected = IssueStatus.NEW;

    final IssueStatus actual = IssueStatus.from(issueStatus);

    assertThat(actual)
        .isEqualTo(expected);
  }
}