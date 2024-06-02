package swe.user.dto;

import static swe.user.domain.UserRole.ADMIN;
import static swe.user.exception.UserExceptionType.USER_REGISTER_ADMIN_NOT_SUPPORTED;

import swe.user.domain.User;
import swe.user.domain.UserRole;
import swe.user.exception.UserException;

public record UserRegisterRequest(String accountId, String roleName, String nickName,
                                  String password) {

  public User toUser() {
    final UserRole userRole = UserRole.from(roleName);
    if (userRole == ADMIN) {
      throw new UserException(USER_REGISTER_ADMIN_NOT_SUPPORTED);
    }
    return User.builder()
        .accountId(accountId)
        .userRole(userRole)
        .nickname(nickName)
        .password(password)
        .build();
  }
}
