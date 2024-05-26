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
      left join fetch i.comments
      where i.id = :id
      """)
  Optional<Issue> findByIdWithComments(final Long id);

  @Query("""
      select i
      from Issue i
      left join fetch i.comments
      join fetch i.reporter
      left join fetch i.fixer
      left join fetch i.assignee
      where i.id = :id
      """)
  Optional<Issue> findByIdWithAll(final Long id);

  @Query("""
      select i
      from Issue i
      join fetch i.reporter
      left join fetch i.assignee
      where i.projectId = :projectId
      """)
  List<Issue> findByProjectIdWithReporterAssignee(final Long projectId);

  default Issue readByIdWithComments(final Long id) {
    return findByIdWithComments(id).orElseThrow(() -> new IssueException(ISSUE_NOT_FOUND));
  }

  default Issue readById(final Long id) {
    return findById(id).orElseThrow(() -> new IssueException(ISSUE_NOT_FOUND));
  }

  default Issue readByIdWithAll(final Long id) {
    return findByIdWithAll(id).orElseThrow(() -> new IssueException(ISSUE_NOT_FOUND));
  }
}
