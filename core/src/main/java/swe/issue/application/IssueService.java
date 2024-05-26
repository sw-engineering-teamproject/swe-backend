package swe.issue.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.issue.domain.Issue;
import swe.issue.domain.IssueFilterCondition;
import swe.issue.domain.IssueRepository;
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
  public void commentContent(final Long memberId, final Long issueId, final String content) {
    final Issue issue = issueRepository.readByIdWithComments(issueId);
    issue.addComment(memberId, content);
  }

  @Transactional
  public void updateDescription(final Long issueId, final String newDescription) {
    final Issue issue = issueRepository.readById(issueId);
    issue.updateDescription(newDescription);
  }
}
