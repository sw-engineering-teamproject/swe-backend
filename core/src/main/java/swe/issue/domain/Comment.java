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
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Comment {

  private static final String INITIAL_PROJECT_COMMENT = "%s created a new issue.";

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn
  @NotNull
  private Issue issue;

  @NotNull
  private Long commenterId;

  @NotNull
  private LocalDateTime createdAt;

  @NotNull
  private String detail;

  public Comment(final Issue issue, final Long commenterId, final String detail) {
    this.issue = issue;
    this.commenterId = commenterId;
    this.detail = detail;
    this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")).truncatedTo(ChronoUnit.SECONDS);
  }

  static Comment createInitialProjectComment(
      final Issue issue, final Long commenterId, final String reporterName
  ) {
    return new Comment(issue, commenterId, String.format(INITIAL_PROJECT_COMMENT, reporterName));
  }
}