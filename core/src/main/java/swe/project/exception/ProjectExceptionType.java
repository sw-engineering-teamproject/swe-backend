package swe.project.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;
import swe.base.BaseExceptionType;

public enum ProjectExceptionType implements BaseExceptionType {

  CREATE_PROJECT_FORBIDDEN(FORBIDDEN, "프로젝트를 생성할 권한이 없습니다."),
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
