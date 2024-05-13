package swe.issue;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import swe.comment.Comment;
import swe.project.Project;

@Entity
public class Issue {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String title;

  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  @OneToMany(mappedBy = "issue", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private List<Comment> comments;
}
