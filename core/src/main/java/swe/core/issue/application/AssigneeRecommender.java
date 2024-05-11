package swe.core.issue.application;

import java.util.List;
import swe.core.issue.Issue;
import swe.core.project.Project;
import swe.core.user.User;

public interface AssigneeRecommender {

  List<User> recommend(final Project project, final Issue issue);
}
