package jdbc.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class IssueList {
    int key;
    String title;
    String reporter;
    String assignee;
    String status;
}
