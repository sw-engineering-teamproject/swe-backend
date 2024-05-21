package swe.user.domain;

import lombok.Getter;

@Getter
public enum UserRole {

  ADMIN("admin"), PL("PL"), TESTER("tester"), DEV("dev");

  private final String name;

  UserRole(final String name) {
    this.name = name;
  }
}
