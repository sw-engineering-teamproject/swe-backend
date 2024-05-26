package swe.dto.issue;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import swe.issue.domain.Issue;
import swe.user.domain.User;

@Builder
public record IssueDetailResponse(
    Long id,
    String title,
    String description,
    UserResponse reporter,
    UserResponse assignee,
    UserResponse fixer,
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    LocalDateTime reportedTime,
    String status,
    String priority
) {

  public static IssueDetailResponse from(final Issue issue) {
    return IssueDetailResponse.builder()
        .id(issue.getId())
        .title(issue.getTitle())
        .description(issue.getDescription())
        .reporter(UserResponse.from(issue.getReporter()))
        .assignee(issue.getAssignee().map(UserResponse::from).orElse(null))
        .fixer(issue.getFixer().map(UserResponse::from).orElse(null))
        .reportedTime(issue.getReportedDate())
        .status(issue.getStatus().getName())
        .priority(issue.getPriority().getName())
        .build();
  }

  public record UserResponse(Long id, String name) {

    public static UserResponse from(final User user) {
      return new UserResponse(user.getId(), user.getNickname());
    }
  }
}
