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

  @NotNull
  private String title;

  @NotNull
  private String description;

  @NotNull
  private Long projectId;

  @ManyToOne(fetch = LAZY)
  @NotNull
  private User reporter;

  @ManyToOne(fetch = LAZY)
  private User fixer;

  @ManyToOne(fetch = LAZY)
  private User assignee;

  @NotNull
  private LocalDateTime reportedDate;

  @Enumerated(value = EnumType.STRING)
  @NotNull
  private IssueStatus status;

  @Enumerated(value = EnumType.STRING)
  @NotNull
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
    issue.comments.add(
        createInitialProjectComment(issue, reporter, reporter.getNickname())
    );
    return issue;
  }

  public boolean isEqualAssigneeId(final Long id) {
    return getAssignee().map(user -> user.getId().equals(id))
        .orElse(false);
  }

  public boolean isEqualReporterId(final Long id) {
    return reporter.getId().equals(id);
  }

  public boolean isEqualStatus(final IssueStatus status) {
    return this.status == status;
  }

  public Optional<User> getAssignee() {
    return Optional.ofNullable(assignee);
  }

  public Optional<User> getFixer() {
    return Optional.ofNullable(fixer);
  }

  public void assignAssignee(final User assignee) {
    this.assignee = assignee;
  }

  public void addComment(final User commenter, final String content) {
    final Comment comment = new Comment(this, commenter, content);
    comments.add(comment);
  }

  public void updateDescription(final String newDescription) {
    this.description = newDescription;
  }

  public void updateStatus(final IssueStatus newIssueStatus) {
    this.status = newIssueStatus;
  }

  public void updatePriority(final IssuePriority newIssuePriority) {
    this.priority = newIssuePriority;
  }
}
