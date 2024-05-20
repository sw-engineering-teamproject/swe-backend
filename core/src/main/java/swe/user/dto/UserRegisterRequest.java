package swe.user.dto;

import swe.user.domain.User;
import swe.user.domain.UserRole;

public record UserRegisterRequest(String accountId, UserRole role, String nickName, String password) {

  public User toUser(){
    return User.builder()
        .accountId(accountId)
        .userRole(role)
        .nickname(nickName)
        .password(password)
        .build();
  }
}
