package swe.base;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

  private final BaseExceptionType exceptionType;

  public BaseException(final BaseExceptionType exceptionType) {
    super(exceptionType.getMessage());
    this.exceptionType = exceptionType;
  }
}
