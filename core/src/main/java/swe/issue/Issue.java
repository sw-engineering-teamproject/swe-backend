package swe.issue;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
}
