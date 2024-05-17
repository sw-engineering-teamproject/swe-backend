package swe.project.application;

import static org.assertj.core.api.Assertions.assertThat;
import static swe.fixture.UserFixture.unsavedUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import swe.project.domain.Project;
import swe.project.domain.ProjectRepository;
import swe.support.ServiceTest;
import swe.user.domain.User;
import swe.user.domain.UserRepository;

class ProjectServiceTest extends ServiceTest {

  @Autowired
  private ProjectService projectService;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ProjectRepository projectRepository;

  @Test
  void 정상적으로_프로젝트를_생성한다() {
    //given
    final User user = userRepository.save(unsavedUser());

    //when
    final Long projectId = projectService.createProject("title", user.getId());

    //then
    final Project actual = projectRepository.readById(projectId);
    final Project expected = new Project("title", user.getId());

    assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(expected);
  }
}