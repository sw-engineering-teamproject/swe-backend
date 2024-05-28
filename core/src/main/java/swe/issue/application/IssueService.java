package swe.issue.application;

import static java.util.stream.Collectors.groupingBy;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.issue.domain.Issue;
import swe.issue.domain.IssueFilterCondition;
import swe.issue.domain.IssuePriority;
import swe.issue.domain.IssueRepository;
import swe.issue.domain.IssueStatus;
import swe.issue.dto.IssueCreateRequest;
import swe.project.domain.ProjectRepository;
import swe.user.domain.User;
import swe.user.domain.UserRepository;

@Service
@RequiredArgsConstructor
public class IssueService {

  private final IssueRepository issueRepository;
  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;

  @Transactional
  public Long createIssue(final Long reporterId, final IssueCreateRequest request) {
    final User reporter = userRepository.readById(reporterId);
    projectRepository.validateProjectExists(request.projectId());
    final Issue newIssue = request.toIssue(reporter);
    return issueRepository.save(newIssue).getId();
  }

  @Transactional(readOnly = true)
  public List<Issue> findIssues(final Long projectId) {
    return issueRepository.findByProjectIdWithReporterAssignee(projectId);
  }

  @Transactional(readOnly = true)
  public List<Issue> filterIssues(
      final Long projectId, final String condition, final String value
  ) {
    final IssueFilterCondition filterCondition = IssueFilterCondition.from(condition);
    return findIssues(projectId).stream()
        .filter(issue -> filterCondition.filterIssue(issue, value))
        .toList();
  }

  @Transactional
  public void commentContent(final Long userId, final Long issueId, final String content) {
    final Issue issue = issueRepository.readByIdWithComments(issueId);
    final User commenter = userRepository.readById(userId);
    issue.addComment(commenter, content);
  }

  @Transactional
  public void updateDescription(final Long issueId, final String newDescription) {
    final Issue issue = issueRepository.readById(issueId);
    issue.updateDescription(newDescription);
  }

  @Transactional
  public void updateStatus(final Long requesterId, final Long issueId, final String statusName) {
    final Issue issue = issueRepository.readById(issueId);
    final User requester = userRepository.readById(requesterId);
    final IssueStatus newStatus = IssueStatus.from(statusName);
    newStatus.validateUserRoleUpdateStatus(requester);
    issue.updateStatus(newStatus);
    if (newStatus == IssueStatus.FIXED) {
      issue.updateFixer(requester);
    }
  }

  @Transactional
  public void updatePriority(final Long issueId, final String priorityName) {
    final Issue issue = issueRepository.readById(issueId);
    final IssuePriority newPriority = IssuePriority.from(priorityName);
    issue.updatePriority(newPriority);
  }

  public List<IssueStatus> getIssueStatuses() {
    return Arrays.stream(IssueStatus.values())
        .toList();
  }

  public List<IssuePriority> getIssuePriority() {
    return Arrays.stream(IssuePriority.values())
        .toList();
  }

  @Transactional
  public void assignUser(final Long userId, final Long issueId, final Long assigneeId) {
    final User newAssignee = userRepository.readById(assigneeId);
    final Issue issue = issueRepository.readById(issueId);
    issue.assignAssignee(newAssignee);
  }

  @Transactional(readOnly = true)
  public Issue findIssueDetail(final Long issueId) {
    return issueRepository.readByIdWithAll(issueId);
  }

  @Transactional(readOnly = true)
  public Map<LocalDate, Long> getIssueCreateCountByDay(final Long projectId) {
    return issueRepository.findByProjectId(projectId).stream()
        .collect(groupingBy(
                issue -> issue.getReportedDate().toLocalDate(),
                Collectors.counting()
            )
        );
  }

  @Transactional(readOnly = true)
  public Map<LocalDate, Long> getIssueCreateCountByMonth(final Long projectId) {
    return issueRepository.findByProjectId(projectId).stream()
        .collect(groupingBy(
                IssueService::removeDay,
                Collectors.counting()
            )
        );
  }

  private static LocalDate removeDay(final Issue issue) {
    final LocalDate reportedDate = issue.getReportedDate().toLocalDate();
    return reportedDate.minusDays(reportedDate.getDayOfMonth()).plusDays(1);
  }

  @Transactional(readOnly = true)
  public Map<IssueStatus, Long> getStatusCount(final Long projectId) {
    return issueRepository.findByProjectId(projectId).stream()
        .collect(groupingBy(
                Issue::getStatus,
                Collectors.counting()
            )
        );
  }

  @Transactional(readOnly = true)
  public Map<IssuePriority, Long> getPriorityCount(final Long projectId) {
    return issueRepository.findByProjectId(projectId).stream()
        .collect(groupingBy(
                Issue::getPriority,
                Collectors.counting()
            )
        );
  }

  @Transactional(readOnly = true)
  public Map<User, Long> getAssigneeCount(final Long projectId) {
    return issueRepository.findByProjectIdWithReporterAssignee(projectId).stream()
        .filter(issue -> issue.getAssignee().isPresent())
        .collect(groupingBy(
                issue -> issue.getAssignee().get(),
                Collectors.counting()
            )
        );
  }

  @Transactional(readOnly = true)
  public Map<User, Long> getReporterCount(final Long projectId) {
    return issueRepository.findByProjectIdWithReporterAssignee(projectId).stream()
        .collect(groupingBy(
                Issue::getReporter,
                Collectors.counting()
            )
        );
  }
}
