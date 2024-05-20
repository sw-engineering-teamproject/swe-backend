package swe.issue.exception;

import org.springframework.http.HttpStatus;
import swe.base.BaseExceptionType;

public enum IssueExceptionType implements BaseExceptionType {

  ISSUE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 이슈를 찾을 수 없습니다");

  private final HttpStatus httpStatus;
  private final String message;

  IssueExceptionType(final HttpStatus httpStatus, final String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @Override
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
