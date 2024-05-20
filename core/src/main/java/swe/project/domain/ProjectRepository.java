package swe.project.domain;

import static swe.project.exception.ProjectExceptionType.NOT_FOUND_PROJECT;

import org.springframework.data.jpa.repository.JpaRepository;
import swe.project.exception.ProjectException;

public interface ProjectRepository extends JpaRepository<Project, Long> {

  default Project readById(final Long id) {
    return findById(id).orElseThrow(() -> new ProjectException(NOT_FOUND_PROJECT));
  }

  default void validateProjectExists(final Long id) {
    if (!existsById(id)) {
      throw new ProjectException(NOT_FOUND_PROJECT);
    }
  }
}
