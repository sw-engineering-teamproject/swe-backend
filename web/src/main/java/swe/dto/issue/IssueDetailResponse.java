package swe.dto.issue;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import swe.dto.user.UserResponse;
import swe.issue.domain.Comment;
import swe.issue.domain.Issue;

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
    String priority,
    List<CommentResponse> comments
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
        .comments(CommentResponse.createList(issue.getComments()))
        .build();
  }

  @Builder
  record CommentResponse(
      Long id,
      UserResponse commenter,
      String content,
      @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
      LocalDateTime createdAt
  ) {

    public static List<CommentResponse> createList(final List<Comment> comments) {
      return comments.stream()
          .map(CommentResponse::from)
          .toList();
    }

    public static CommentResponse from(final Comment comment) {
      return CommentResponse.builder()
          .id(comment.getId())
          .content(comment.getContent())
          .createdAt(comment.getCreatedAt())
          .commenter(UserResponse.from(comment.getCommenter()))
          .build();
    }
  }
}
