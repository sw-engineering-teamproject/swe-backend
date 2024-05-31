package swe.issue.application;

import java.util.List;
import swe.issue.domain.Issue;
import swe.user.domain.User;

public interface AssigneeRecommender {

  List<User> recommend(final Long projectId, final Issue issue);

  void addNewIssuesToVectorDB(final List<Issue> issues);
}
