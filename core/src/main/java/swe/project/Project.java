package swe.project;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import swe.issue.Issue;
import swe.user.User;

@Entity
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String projectName;

  @ManyToOne(fetch = FetchType.LAZY)
  private User creator;

  @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private List<Issue> issues;
}
