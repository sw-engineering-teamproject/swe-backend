package swe.project.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import swe.project.domain.Project;
import swe.user.domain.User;

public record ProjectOverviewResponse(String title, String reporterName) {

  public static List<ProjectOverviewResponse> createList(
      final List<Project> projects, final List<User> users
  ) {
    final Map<Long, String> idUserNameMap = users.stream()
        .collect(Collectors.toMap(User::getId, User::getNickName));

    return projects.stream()
        .map(project -> new ProjectOverviewResponse(
            project.getTitle(), idUserNameMap.get(project.getReporterId()))
        )
        .toList();
  }
}
