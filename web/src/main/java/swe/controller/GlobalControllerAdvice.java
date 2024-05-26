package swe.controller;

import java.net.BindException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import swe.base.BaseException;
import swe.base.BaseExceptionType;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ExceptionResponse> handleException(final BaseException e) {
    final BaseExceptionType type = e.getExceptionType();

    if (type.getHttpStatus().is5xxServerError()) {
      log.error("[ERROR] MESSAGE : {}, ", type.getMessage(), e);
      return new ResponseEntity<>(ExceptionResponse.from(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    log.warn("[WARN] MESSAGE: {}", type.getMessage());
    log.debug("stackTrace : ", e);
    return new ResponseEntity<>(ExceptionResponse.from(e), type.getHttpStatus());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(
      final HttpMessageNotReadableException e
  ) {
    final String message = "입력 형식이 올바르지 않습니다.";
    log.warn("[WARN] MESSAGE: {}", message);
    return ResponseEntity.badRequest().body(new ExceptionResponse(message));
  }

  @ExceptionHandler({BindException.class, MissingServletRequestParameterException.class})
  public ResponseEntity<ExceptionResponse> handleBindExceptionHandler(final BindException e) {
    final String message = "요청 파라미터가 올바르지 않습니다.";
    log.warn("[WARN] MESSAGE: " + message);
    return ResponseEntity.badRequest().body(new ExceptionResponse(message));
  }

  @Getter
  public static class ExceptionResponse {

    private final String message;

    private ExceptionResponse(final String message) {
      this.message = message;
    }

    private static ExceptionResponse from(final BaseException e) {
      final BaseExceptionType type = e.getExceptionType();
      return new ExceptionResponse(type.getMessage());
    }
  }
}
