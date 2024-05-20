package swe.issue.domain;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static swe.issue.domain.IssuePriority.MAJOR;
import static swe.issue.domain.IssueStatus.NEW;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Issue {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String title;

  private String description;

  @NotNull
  private Long projectId;

  @NotNull
  private Long reporterId;

  @NotNull
  private Long fixerId;

  @NotNull
  private Long assigneeId;

  private LocalDateTime reportedDate;

  @Enumerated(value = EnumType.STRING)
  private IssueStatus status;

  @Enumerated(value = EnumType.STRING)
  private IssuePriority priority;

  @OneToMany(mappedBy = "issue", orphanRemoval = true, cascade = PERSIST)
  private List<Comment> comments = new ArrayList<>();

  @Builder
  public Issue(final String title, final String description, final Long projectId) {
    this.title = title;
    this.description = description;
    this.projectId = projectId;
    this.status = NEW;
    this.priority = MAJOR;
  }
}
