package swe.issue.application;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.issue.domain.Comment;
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
  public void updateStatus(final Long issueId, final String statusName) {
    final Issue issue = issueRepository.readById(issueId);
    final IssueStatus newStatus = IssueStatus.from(statusName);
    issue.updateStatus(newStatus);
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
  public void assignUser(final Long issueId, final Long assigneeId) {
    final User newAssignee = userRepository.readById(assigneeId);
    final Issue issue = issueRepository.readById(issueId);
    issue.assignAssignee(newAssignee);
  }

  @Transactional(readOnly = true)
  public Issue findIssueDetail(final Long issueId) {
    return issueRepository.readByIdWithRelatedUsers(issueId);
  }
}
