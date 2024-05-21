package swe.issue.domain;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static swe.issue.domain.Comment.createInitialProjectComment;
import static swe.issue.domain.IssuePriority.MAJOR;
import static swe.issue.domain.IssueStatus.NEW;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swe.user.domain.User;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Issue {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String title;

  private String description;

  @NotNull
  private Long projectId;

  @ManyToOne(fetch = LAZY)
  @NotNull
  private User reporter;

  private Long fixerId;

  @ManyToOne(fetch = LAZY)
  private User assignee;

  private LocalDateTime reportedDate;

  @Enumerated(value = EnumType.STRING)
  private IssueStatus status;

  @Enumerated(value = EnumType.STRING)
  private IssuePriority priority;

  @OneToMany(mappedBy = "issue", orphanRemoval = true, cascade = PERSIST)
  private List<Comment> comments = new ArrayList<>();

  @Builder
  public Issue(
      final String title, final String description, final Long projectId, final User reporter
  ) {
    this.title = title;
    this.description = description;
    this.projectId = projectId;
    this.reporter = reporter;
    this.status = NEW;
    this.priority = MAJOR;
    this.reportedDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")).truncatedTo(ChronoUnit.SECONDS);
  }

  public static Issue createIssue(
      final String title, final String description, final Long projectId, final User reporter
  ) {
    final Issue issue = new Issue(title, description, projectId, reporter);
    issue.addComment(createInitialProjectComment(issue, reporter.getId(), reporter.getNickname()));
    return issue;
  }

  public void addComment(final Comment comment) {
    comments.add(comment);
  }

  public Optional<User> getAssignee() {
    return Optional.ofNullable(assignee);
  }
}
