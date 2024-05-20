package swe.issue.domain;

import static swe.issue.exception.IssueExceptionType.ISSUE_NOT_FOUND;

import org.springframework.data.jpa.repository.JpaRepository;
import swe.issue.exception.IssueException;

public interface IssueRepository extends JpaRepository<Issue, Long> {

  default Issue readById(final Long userId) {
    return findById(userId).orElseThrow(() -> new IssueException(ISSUE_NOT_FOUND));
  }
}
