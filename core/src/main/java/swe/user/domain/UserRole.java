package swe.user.domain;

import static java.util.Arrays.stream;
import static swe.user.exception.UserExceptionType.USER_ROLE_NOT_FOUND;

import java.util.Objects;
import lombok.Getter;
import swe.user.exception.UserException;

@Getter
public enum UserRole {

  ADMIN("admin"), PL("PL"), TESTER("tester"), DEV("dev");

  private final String name;

  UserRole(final String name) {
    this.name = name;
  }

  public static UserRole from(final String userRoleName) {
    return stream(values())
        .filter(status -> Objects.equals(status.name, userRoleName))
        .findAny()
        .orElseThrow(() -> new UserException(USER_ROLE_NOT_FOUND));
  }
}
