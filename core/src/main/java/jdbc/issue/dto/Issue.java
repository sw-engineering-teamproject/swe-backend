package jdbc.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Issue {
    String title;
    String description;
    String reporter;
    String time;
    String fixer;
    String assignee;
    String priority;
    String status;
}
