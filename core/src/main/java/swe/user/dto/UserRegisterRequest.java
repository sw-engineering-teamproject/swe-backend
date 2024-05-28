package swe.user.dto;

import swe.user.domain.User;
import swe.user.domain.UserRole;

public record UserRegisterRequest(String accountId, String roleName, String nickName,
                                  String password) {

  public User toUser() {
    return User.builder()
        .accountId(accountId)
        .userRole(UserRole.from(roleName))
        .nickname(nickName)
        .password(password)
        .build();
  }
}
