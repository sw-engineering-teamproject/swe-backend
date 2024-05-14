package swe.user.dto;

import swe.user.User;
import swe.user.domain.UserRole;

public record UserRegisterRequest(String id, UserRole role, String nickName, String password) {

  public User toUser(){
    return User.builder()
        .accountId(id)
        .userRole(role)
        .nickName(nickName)
        .password(password)
        .build();
  }
}
