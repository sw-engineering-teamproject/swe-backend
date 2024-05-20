package swe.issue.domain;

import static swe.issue.exception.IssueExceptionType.ISSUE_NOT_FOUND;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swe.issue.exception.IssueException;

public interface IssueRepository extends JpaRepository<Issue, Long> {

  @Query("""
      select i
      from Issue i
      join fetch i.comments
      where i.id = :id
      """)
  Optional<Issue> findByIdWithComments(final Long id);

  default Issue readByIdWithComments(final Long id) {
    return findByIdWithComments(id).orElseThrow(() -> new IssueException(ISSUE_NOT_FOUND));
  }

  List<Issue> findByProjectId(final Long projectId);
}
