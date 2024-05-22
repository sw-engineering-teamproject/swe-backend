package swe.fixture;

import swe.user.domain.User;
import swe.user.domain.UserRole;

public class UserFixture {

  public static final String USER_ACCOUNT_ID = "hong";
  public static final String PASSWORD = "password";

  public static User id가_없는_유저() {
    return User.builder()
        .nickname("닉네임")
        .userRole(UserRole.PL)
        .accountId(USER_ACCOUNT_ID)
        .password(PASSWORD)
        .build();
  }

  public static User id가_없는_유저2() {
    return User.builder()
        .nickname("닉네임")
        .userRole(UserRole.PL)
        .accountId("hong2")
        .password("password2")
        .build();
  }

  public static User id가_있는_유저() {
    return new User(1L, "accountId", "password", "nickName", UserRole.PL);
  }
}
