package swe.gui;

import swe.issue.domain.Issue;
import swe.project.dto.ProjectOverviewResponse;
import swe.user.domain.User;

public class SessionStorage {

    public static User loginUser;
    public static ProjectOverviewResponse currentProject;
    public static Issue currentIssue;
}
