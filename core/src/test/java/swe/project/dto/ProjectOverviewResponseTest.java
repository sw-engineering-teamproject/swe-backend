package swe.project.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import swe.project.domain.Project;
import swe.user.domain.User;
import swe.user.domain.UserRole;

class ProjectOverviewResponseTest {

  @Test
  void 유저_프로젝트를_리스트형으로_변환한다() {
    final User user1 = new User(10L, "account", "password", "user1", UserRole.ADMIN);
    final User user2 = new User(11L, "account", "password", "user2", UserRole.ADMIN);

    final Project project1 = new Project(1L, "project1", user1.getId());
    final Project project2 = new Project(2L, "project2", user1.getId());
    final Project project3 = new Project(3L, "project3", user2.getId());

    final List<ProjectOverviewResponse> expected = List.of(
        new ProjectOverviewResponse(1L, "project1", "user1"),
        new ProjectOverviewResponse(2L, "project2", "user1"),
        new ProjectOverviewResponse(3L, "project3", "user2")
    );

    final List<ProjectOverviewResponse> actual = ProjectOverviewResponse.createList(
        List.of(project1, project2, project3), List.of(user1, user2));

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(expected);
  }
}