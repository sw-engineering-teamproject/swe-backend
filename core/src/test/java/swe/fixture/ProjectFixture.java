package swe.fixture;

import swe.project.domain.Project;

public class ProjectFixture {

  public static final String TITLE = "title";

  public static Project unsavedProject(final Long reporterId) {
    return Project.builder()
        .title(TITLE)
        .reporterId(reporterId)
        .build();
  }
}
