package swe.project.exception;

import swe.base.BaseException;

public class ProjectException extends BaseException {

  public ProjectException(final ProjectExceptionType projectExceptionType) {
    super(projectExceptionType);
  }
}
