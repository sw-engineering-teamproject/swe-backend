package swe.core.issue;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import swe.core.comment.Comment;
import swe.core.project.Project;

@Entity
public class Issue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  @OneToMany(mappedBy = "issue", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private List<Comment> comments;
}
