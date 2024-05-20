package swe.project.application;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.project.domain.Project;
import swe.project.domain.ProjectRepository;
import swe.project.dto.ProjectOverviewResponse;
import swe.user.domain.User;
import swe.user.domain.UserRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  @Transactional
  public Long createProject(final String title, final Long reporterId) {
    final User reporter = userRepository.readById(reporterId);
    final Project project = Project.builder()
        .reporterId(reporter.getId())
        .title(title)
        .build();

    return projectRepository.save(project).getId();
  }

  public List<ProjectOverviewResponse> readAllProject() {
    final List<Project> projects = projectRepository.findAll();
    final Set<Long> reporterIds = projects.stream()
        .map(Project::getReporterId)
        .collect(toSet());
    final List<User> reporters = userRepository.findAllById(reporterIds);

    return ProjectOverviewResponse.createList(projects, reporters);
  }
}
