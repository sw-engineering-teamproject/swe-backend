package swe.user.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;
import swe.base.BaseExceptionType;

public enum UserExceptionType implements BaseExceptionType {

  NOT_FOUND_AUTHORIZATION_TOKEN(BAD_REQUEST, "인증 토큰을 찾을 수 없습니다."),
  INVALID_ACCESS_TOKEN_TYPE(BAD_REQUEST, "Access Token Type이 올바르지 않습니다.");

  private final HttpStatus httpStatus;
  private final String message;

  UserExceptionType(final HttpStatus httpStatus, final String message) {
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
