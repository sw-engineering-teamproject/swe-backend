package swe.issue.application;

import java.util.List;
import swe.issue.Issue;
import swe.project.domain.Project;
import swe.user.domain.User;

public interface AssigneeRecommender {

  List<User> recommend(final Project project, final Issue issue);
}
