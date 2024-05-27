package swe.fixture;

import java.time.LocalDateTime;
import swe.issue.domain.Issue;
import swe.user.domain.User;

public class IssueFixture {

  public static Issue id가_없는_Issue(final User reporter, final Long projectId) {
    return Issue.builder()
        .title("title")
        .description("description")
        .projectId(projectId)
        .reporter(reporter)
        .reportedDate(LocalDateTime.now())
        .build();
  }
}
