package swe.core.base;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {

  HttpStatus getHttpStatus();

  String getMessage();
}
