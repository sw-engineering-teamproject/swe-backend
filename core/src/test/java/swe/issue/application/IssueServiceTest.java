package swe.issue.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static swe.fixture.IssueFixture.id가_없는_Issue;
import static swe.fixture.ProjectFixture.id가_없는_Project;
import static swe.fixture.UserFixture.id가_없는_유저;
import static swe.fixture.UserFixture.id가_없는_유저2;
import static swe.issue.domain.IssuePriority.BLOCKER;
import static swe.issue.domain.IssuePriority.CRITICAL;
import static swe.issue.domain.IssuePriority.MAJOR;
import static swe.issue.domain.IssueStatus.ASSIGNED;
import static swe.issue.domain.IssueStatus.FIXED;
import static swe.issue.domain.IssueStatus.NEW;
import static swe.issue.domain.IssueStatus.RESOLVED;
import static swe.user.domain.UserRole.TESTER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import swe.issue.domain.Comment;
import swe.issue.domain.Issue;
import swe.issue.domain.IssuePriority;
import swe.issue.domain.IssueRepository;
import swe.issue.domain.IssueStatus;
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
    final Project project = projectRepository.save(id가_없는_Project(user.getId()));
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
    final Project project = projectRepository.save(id가_없는_Project(user.getId()));
    final IssueCreateRequest request
        = new IssueCreateRequest("issue title", "new issue", project.getId());
    issueService.createIssue(user.getId(), request);
    issueService.createIssue(user.getId(), request);

    //when
    final List<Issue> actual = issueService.findIssues(project.getId());

    //then
    final List<Issue> expected = List.of(
        new Issue("issue title", "new issue", project.getId(), user, LocalDateTime.now()),
        new Issue("issue title", "new issue", project.getId(), user, LocalDateTime.now())
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
    final Project project = projectRepository.save(id가_없는_Project(user1.getId()));

    final IssueCreateRequest request
        = new IssueCreateRequest("issue title", "new issue", project.getId());

    issueService.createIssue(user1.getId(), request);
    issueService.createIssue(user2.getId(), request);

    //when
    final List<Issue> actual
        = issueService.filterIssues(project.getId(), "reporter", user1.getId().toString());

    //then
    final List<Issue> expected = List.of(
        new Issue("issue title", "new issue", project.getId(), user1, LocalDateTime.now())
    );

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("reportedDate", "comments", "id")
        .containsExactlyInAnyOrderElementsOf(expected);
  }

  @Test
  void 이슈에_댓글을_추가한다() {
    //given
    final User user = userRepository.save(id가_없는_유저());
    final User commenter = userRepository.save(id가_없는_유저2());
    final Project project = projectRepository.save(id가_없는_Project(user.getId()));
    final Issue issue = issueRepository.save(id가_없는_Issue(user, project.getId()));

    //when
    issueService.commentContent(commenter.getId(), issue.getId(), "새로운 댓글");

    //then
    final Issue actual = issueRepository.readByIdWithComments(issue.getId());
    final Comment expected = new Comment(actual, commenter, "새로운 댓글");
    assertThat(actual.getComments())
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "commenter")
        .contains(expected);
  }

  @Test
  void 이슈의_설명을_업데이트한다() {
    //given
    final User user = userRepository.save(id가_없는_유저());
    final Project project = projectRepository.save(id가_없는_Project(user.getId()));
    final Issue issue = issueRepository.save(id가_없는_Issue(user, project.getId()));
    final String description = "새로운 설명";

    //when
    issueService.updateDescription(issue.getId(), description);

    //then
    final Issue updatedIssue = issueRepository.readById(issue.getId());

    assertThat(updatedIssue.getDescription())
        .isEqualTo(description);
  }

  @Test
  void 이슈의_상태를_업데이트한다() {
    //given
    final User user = userRepository.save(id가_없는_유저());
    final Project project = projectRepository.save(id가_없는_Project(user.getId()));
    final Issue issue = issueRepository.save(id가_없는_Issue(user, project.getId()));
    final String newStatusName = ASSIGNED.getName();

    //when
    issueService.updateStatus(user.getId(), issue.getId(), newStatusName);

    //then
    final Issue updatedIssue = issueRepository.readById(issue.getId());

    assertThat(updatedIssue.getStatus())
        .isEqualTo(ASSIGNED);
  }

  @Test
  void 이슈의상태를_업데이트할떄_Fixed를_업데이트하면_Fixer가_된다() {
    //given
    final User user = userRepository.save(id가_없는_유저());
    final Project project = projectRepository.save(id가_없는_Project(user.getId()));
    final Issue issue = issueRepository.save(id가_없는_Issue(user, project.getId()));
    final String newStatusName = FIXED.getName();

    //when
    issueService.updateStatus(user.getId(), issue.getId(), newStatusName);

    //then
    final Issue updatedIssue = issueRepository.readByIdWithAll(issue.getId());

    assertAll(
        () -> assertThat(updatedIssue.getStatus())
            .isEqualTo(FIXED),
        () -> assertThat(updatedIssue.getFixer().orElseThrow())
            .usingRecursiveComparison()
            .isEqualTo(user)
    );
  }

  @Test
  void 이슈의_우선순위를_업데이트한다() {
    //given
    final User user = userRepository.save(id가_없는_유저());
    final Project project = projectRepository.save(id가_없는_Project(user.getId()));
    final Issue issue = issueRepository.save(id가_없는_Issue(user, project.getId()));
    final String newPriorityName = CRITICAL.getName();

    //when
    issueService.updatePriority(issue.getId(), newPriorityName);

    //then
    final Issue updatedIssue = issueRepository.readById(issue.getId());

    assertThat(updatedIssue.getPriority())
        .isEqualTo(CRITICAL);
  }

  @Test
  void 이슈의_assignee를_업데이트_한다() {
    //given
    final User user = userRepository.save(id가_없는_유저());
    final Project project = projectRepository.save(id가_없는_Project(user.getId()));
    final Issue issue = issueRepository.save(id가_없는_Issue(user, project.getId()));
    final User newAssignee = userRepository.save(id가_없는_유저2());

    //when
    issueService.assignUser(issue.getId(), newAssignee.getId());

    //then
    final Issue updatedIssue = issueRepository.readById(issue.getId());
    final Long newAssigneeId = updatedIssue.getAssignee().map(User::getId).orElseThrow();

    assertThat(newAssigneeId)
        .isEqualTo(newAssignee.getId());
  }

  @Test
  void 이슈를_상세조회_한다() {
    //given
    final User user = userRepository.save(id가_없는_유저());
    final Project project = projectRepository.save(id가_없는_Project(user.getId()));
    final Issue issue = issueRepository.save(id가_없는_Issue(user, project.getId()));

    final User assignee = userRepository.save(id가_없는_유저2());
    issueService.assignUser(issue.getId(), assignee.getId());

    //when
    final Issue actual = issueService.findIssueDetail(issue.getId());

    //then
    final Issue expected = new Issue(issue.getTitle(), issue.getDescription(), issue.getProjectId(),
        user, LocalDateTime.now());
    expected.assignAssignee(assignee);

    assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFields("id", "reportedDate")
        .isEqualTo(expected);
  }

  @Test
  void 이슈를_일자별로_생성수를_조회한다() {
    //given
    final User user = userRepository.save(id가_없는_유저());
    final Project project = projectRepository.save(id가_없는_Project(user.getId()));
    final List<Issue> issues = List.of(
        new Issue("title", "des", project.getId(), user,
            LocalDateTime.of(2023, 11, 2, 10, 10)),
        new Issue("title", "des", project.getId(), user,
            LocalDateTime.of(2023, 11, 3, 10, 10)),
        new Issue("title", "des", project.getId(), user,
            LocalDateTime.of(2023, 11, 4, 10, 10)),
        new Issue("title", "des", project.getId(), user,
            LocalDateTime.of(2023, 11, 2, 10, 10))
    );
    issueRepository.saveAll(issues);

    //when
    final Map<LocalDate, Long> actual
        = issueService.getIssueCreateCountByDay(project.getId());

    //then
    final Map<LocalDate, Long> expected = Map.of(
        LocalDate.of(2023, 11, 2), 2L,
        LocalDate.of(2023, 11, 3), 1L,
        LocalDate.of(2023, 11, 4), 1L
    );

    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void 이슈를_월별로_생성수를_조회한다() {
    //given
    final User user = userRepository.save(id가_없는_유저());
    final Project project = projectRepository.save(id가_없는_Project(user.getId()));
    final List<Issue> issues = List.of(
        new Issue("title", "des", project.getId(), user,
            LocalDateTime.of(2023, 12, 2, 10, 10)),
        new Issue("title", "des", project.getId(), user,
            LocalDateTime.of(2023, 11, 3, 10, 10)),
        new Issue("title", "des", project.getId(), user,
            LocalDateTime.of(2023, 11, 4, 10, 10)),
        new Issue("title", "des", project.getId(), user,
            LocalDateTime.of(2023, 11, 2, 10, 10))
    );
    issueRepository.saveAll(issues);

    //when
    final Map<LocalDate, Long> actual
        = issueService.getIssueCreateCountByMonth(project.getId());

    //then
    final Map<LocalDate, Long> expected = Map.of(
        LocalDate.of(2023, 11, 1), 3L,
        LocalDate.of(2023, 12, 1), 1L
    );

    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void 우선순위를_기준으로_이슈를_조회한다() {
    //given
    final User user = userRepository.save(id가_없는_유저());
    final Project project = projectRepository.save(id가_없는_Project(user.getId()));

    final Issue issue1 = id가_없는_Issue(user, project.getId());
    issue1.updatePriority(CRITICAL);
    final Issue issue2 = id가_없는_Issue(user, project.getId());
    issue2.updatePriority(CRITICAL);
    final Issue issue3 = id가_없는_Issue(user, project.getId());
    final Issue issue4 = id가_없는_Issue(user, project.getId());
    issue4.updatePriority(BLOCKER);

    issueRepository.saveAll(List.of(issue1, issue2, issue3, issue4));

    //when
    final Map<IssuePriority, Long> actual
        = issueService.getPriorityCount(project.getId());

    //then
    final Map<IssuePriority, Long> expected = Map.of(
        CRITICAL, 2L,
        BLOCKER, 1L,
        MAJOR, 1L
    );

    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void 이슈상태를_기준으로_이슈를_조회한다() {
    //given
    final User user = userRepository.save(id가_없는_유저());
    final Project project = projectRepository.save(id가_없는_Project(user.getId()));

    final Issue issue1 = id가_없는_Issue(user, project.getId());
    issue1.updateStatus(RESOLVED);
    final Issue issue2 = id가_없는_Issue(user, project.getId());
    issue2.updateStatus(RESOLVED);
    final Issue issue3 = id가_없는_Issue(user, project.getId());
    final Issue issue4 = id가_없는_Issue(user, project.getId());
    issue4.updateStatus(FIXED);

    issueRepository.saveAll(List.of(issue1, issue2, issue3, issue4));

    //when
    final Map<IssueStatus, Long> actual = issueService.getStatusCount(project.getId());

    //then
    final Map<IssueStatus, Long> expected = Map.of(
        RESOLVED, 2L,
        FIXED, 1L,
        NEW, 1L
    );

    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }
}
