package swe.gui;

import org.springframework.context.ApplicationContext;
import swe.issue.application.IssueService;
import swe.issue.domain.Issue;
import swe.project.application.ProjectService;
import swe.project.dto.ProjectOverviewResponse;
import swe.user.application.UserService;
import swe.user.domain.User;

public class SessionStorage {

    public static User loginUser;
    public static ProjectOverviewResponse currentProject;
    public static Issue currentIssue;
    public static UserService userService;
    public static ProjectService projectService;
    public static IssueService issueService;
}
