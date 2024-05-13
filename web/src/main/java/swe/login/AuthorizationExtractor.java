package swe.login;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static swe.user.exception.UserExceptionType.INVALID_ACCESS_TOKEN_TYPE;
import static swe.user.exception.UserExceptionType.NOT_FOUND_AUTHORIZATION_TOKEN;

import jakarta.servlet.http.HttpServletRequest;
import swe.user.exception.UserException;

public class AuthorizationExtractor {

  private static final String BEARER_TYPE = "Bearer";

  public static String extract(final HttpServletRequest request) {
    final String authorizationHeader = request.getHeader(AUTHORIZATION);

    validateAuthorizationHeader(authorizationHeader);

    return authorizationHeader.substring(BEARER_TYPE.length()).trim();
  }

  private static void validateAuthorizationHeader(final String authorizationHeader) {
    if (authorizationHeader == null || authorizationHeader.isBlank()) {
      throw new UserException(NOT_FOUND_AUTHORIZATION_TOKEN);
    }
    if (!authorizationHeader.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
      throw new UserException(INVALID_ACCESS_TOKEN_TYPE);
    }
  }
}
