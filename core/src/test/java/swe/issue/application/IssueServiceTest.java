package swe.issue.application;

import static org.assertj.core.api.Assertions.assertThat;
import static swe.fixture.ProjectFixture.unsavedProject;
import static swe.fixture.UserFixture.unsavedUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import swe.issue.domain.Issue;
import swe.issue.domain.IssueRepository;
import swe.issue.dto.IssueCreateRequest;
import swe.project.domain.Project;
import swe.project.domain.ProjectRepository;
import swe.support.ServiceTest;
import swe.user.domain.User;
import swe.user.domain.UserRepository;

class IssueServiceTest extends ServiceTest {

  @Autowired
  private IssueService issueService;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ProjectRepository projectRepository;
  @Autowired
  private IssueRepository issueRepository;

  @Test
  void 이슈를_생성한다() {
    //given
    final User user = userRepository.save(unsavedUser());
    final Project project = projectRepository.save(unsavedProject(user.getId()));
    final IssueCreateRequest request
        = new IssueCreateRequest("issue title", "new issue", project.getId());

    //when
    final Long issueId = issueService.createIssue(user.getId(), request);

    //then
    final Issue actual = issueRepository.readByIdWithComments(issueId);
    final Issue expected = Issue.builder()
        .title(request.title())
        .reporterId(user.getId())
        .description(request.description())
        .projectId(project.getId())
        .build();

    assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFields("id", "reportedDate", "comments")
        .isEqualTo(expected);
    assertThat(actual.getComments().size())
        .isEqualTo(1);
  }
}