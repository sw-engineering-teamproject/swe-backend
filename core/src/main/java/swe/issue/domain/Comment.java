package swe.issue.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Comment {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne(fetch = LAZY)
  private Issue issue;

  private Long commenterId;

  private LocalDateTime createdAt;

  public Comment(final Issue issue, final Long commenterId) {
    this.issue = issue;
    this.commenterId = commenterId;
    this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
  }
}
