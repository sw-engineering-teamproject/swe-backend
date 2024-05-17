package swe.project.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;
import swe.base.BaseExceptionType;

public enum ProjectExceptionType implements BaseExceptionType {

  NOT_FOUND_PROJECT(NOT_FOUND, "해당하는 프로젝트를 찾을 수 없습니다.");

  private final HttpStatus httpStatus;
  private final String message;

  ProjectExceptionType(final HttpStatus httpStatus, final String message) {
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
