package swe.user.exception;

import swe.base.BaseException;

public class UserException extends BaseException {

  public UserException(final UserExceptionType userExceptionType) {
    super(userExceptionType);
  }
}
