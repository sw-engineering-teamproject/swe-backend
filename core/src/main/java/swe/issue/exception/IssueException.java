package swe.issue.exception;

import swe.base.BaseException;

public class IssueException extends BaseException {

  public IssueException(final IssueExceptionType issueExceptionType) {
    super(issueExceptionType);
  }
}
