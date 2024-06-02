package swe.user.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;
import swe.base.BaseExceptionType;

public enum UserExceptionType implements BaseExceptionType {

  USER_ROLE_NOT_FOUND(NOT_FOUND, "입력한 UserRole을 찾을 수 없습니다."),
  USER_REGISTER_ADMIN_NOT_SUPPORTED(BAD_REQUEST, "Admin은 다르게 추가해주세요"),
  NOT_FOUND_AUTHORIZATION_TOKEN(BAD_REQUEST, "인증 토큰을 찾을 수 없습니다."),
  USER_NOT_FOUND(NOT_FOUND, "유저를 찾을 수 없습니다."),
  USER_PASSWORD_NOT_EQUAL(UNAUTHORIZED, "유저의 비밀번호가 일치하지 않습니다"),
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
