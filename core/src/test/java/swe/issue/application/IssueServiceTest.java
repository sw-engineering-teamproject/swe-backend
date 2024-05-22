package swe.issue.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static swe.fixture.ProjectFixture.unsavedProject;
import static swe.fixture.UserFixture.id가_없는_유저;
import static swe.user.domain.UserRole.TESTER;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
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
  @Autowired
  private TransactionTemplate txTemplate;

  @Test
  void 이슈를_생성한다() {
    //given
    final User user = userRepository.save(id가_없는_유저());
    final Project project = projectRepository.save(unsavedProject(user.getId()));
    final IssueCreateRequest request
        = new IssueCreateRequest("issue title", "new issue", project.getId());

    //when
    final Long issueId = issueService.createIssue(user.getId(), request);

    //then
    final Issue actual = issueRepository.readByIdWithComments(issueId);

    assertAll(
        () -> assertThat(actual.getTitle())
            .isEqualTo(request.title()),
        () -> assertThat(actual.getDescription())
            .isEqualTo(request.description()),
        () -> assertThat(actual.getProjectId())
            .isEqualTo(project.getId()),
        () -> assertThat(actual.getComments().size())
            .isEqualTo(1)
    );
  }

  @Test
  void 이슈를_조회한다() {
    //given
    final User user = userRepository.save(id가_없는_유저());
    final Project project = projectRepository.save(unsavedProject(user.getId()));
    final IssueCreateRequest request
        = new IssueCreateRequest("issue title", "new issue", project.getId());
    issueService.createIssue(user.getId(), request);
    issueService.createIssue(user.getId(), request);

    //when
    final List<Issue> actual = issueService.findIssues(project.getId());

    //then
    final List<Issue> expected = List.of(
        new Issue("issue title", "new issue", project.getId(), user),
        new Issue("issue title", "new issue", project.getId(), user)
    );

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("reportedDate", "comments", "id")
        .containsExactlyInAnyOrderElementsOf(expected);
  }

  @Test
  void 이슈를_필터링한다() {
    //given
    final User user1 = userRepository.save(id가_없는_유저());
    final User user2 = userRepository.save(
        new User("accountId2", "password2", "nickName2", TESTER)
    );
    final Project project = projectRepository.save(unsavedProject(user1.getId()));

    final IssueCreateRequest request
        = new IssueCreateRequest("issue title", "new issue", project.getId());

    issueService.createIssue(user1.getId(), request);
    issueService.createIssue(user2.getId(), request);

    //when
    final List<Issue> actual
        = issueService.filterIssues(project.getId(), "reporter", user1.getId().toString());

    //then
    final List<Issue> expected = List.of(
        new Issue("issue title", "new issue", project.getId(), user1)
    );

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("reportedDate", "comments", "id")
        .containsExactlyInAnyOrderElementsOf(expected);
  }
}