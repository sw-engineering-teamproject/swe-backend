package jdbc.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class IssueEnroll {
    int projectKey;
    int userKey;
    String title;
    String description;
}
