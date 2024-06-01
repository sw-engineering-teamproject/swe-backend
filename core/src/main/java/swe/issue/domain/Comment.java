package swe.issue.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import swe.user.domain.User;

@Entity
@NoArgsConstructor(access = PROTECTED)
@ToString
@Getter
public class Comment {

  private static final String INITIAL_PROJECT_COMMENT = "%s created a new issue.";

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn
  @NotNull
  private Issue issue;

  @ManyToOne(fetch = LAZY)
  @NotNull
  private User commenter;

  @NotNull
  private LocalDateTime createdAt;

  @NotNull
  private String content;

  public Comment(final Issue issue, final User commenter, final String content) {
    this.issue = issue;
    this.commenter = commenter;
    this.content = content;
    this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")).truncatedTo(ChronoUnit.SECONDS);
  }

  static Comment createInitialIssueComment(
      final Issue issue, final User commenter, final String reporterName
  ) {
    return new Comment(issue, commenter, String.format(INITIAL_PROJECT_COMMENT, reporterName));
  }
}