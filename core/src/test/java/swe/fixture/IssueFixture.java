package swe.fixture;

import swe.issue.domain.Issue;
import swe.user.domain.User;

public class IssueFixture {

  public static Issue id가_없는_Issue(final User reporter, final Long projectId) {
    return new Issue("title", "description", projectId, reporter);
  }
}
