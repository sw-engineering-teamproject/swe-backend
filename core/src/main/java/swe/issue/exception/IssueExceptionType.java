package swe.issue.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;
import swe.base.BaseExceptionType;

public enum IssueExceptionType implements BaseExceptionType {

  ISSUE_NOT_FOUND(NOT_FOUND, "해당하는 이슈를 찾을 수 없습니다"),
  ISSUE_STATUS_NOT_FOUND(NOT_FOUND, "해당하는 이슈 상태를 찾을 수 없습니다"),
  ISSUE_PRIORITY_NOT_FOUND(NOT_FOUND, "해당하는 이슈 우선순위를 찾을 수 없습니다"),
  ISSUE_FILTER_CONDITION_NOT_FOUND(NOT_FOUND, "이슈 필터 조건을 찾을 수 없습니다.");

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
