package swe.project.domain;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swe.user.domain.User;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private Long creatorId;

  @Builder
  public Project(final String title, final Long creatorId) {
    this.title = title;
    this.creatorId = creatorId;
  }
}
