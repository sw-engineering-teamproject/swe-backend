package swe.project.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.project.domain.Project;
import swe.project.domain.ProjectRepository;
import swe.user.domain.User;
import swe.user.domain.UserRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  @Transactional
  public Long createProject(final String title, final Long creatorId) {
    final User creator = userRepository.readById(creatorId);
    final Project project = Project.builder()
        .creatorId(creator.getId())
        .title(title)
        .build();

    return projectRepository.save(project).getId();
  }
}
