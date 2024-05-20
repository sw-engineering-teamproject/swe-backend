package swe.issue.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.issue.domain.Issue;
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
}
