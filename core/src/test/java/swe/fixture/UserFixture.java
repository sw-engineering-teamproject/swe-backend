package swe.fixture;

import swe.user.domain.User;
import swe.user.domain.UserRole;

public class UserFixture {

  public static final String USER_ACCOUNT_ID = "hong";
  public static final String PASSWORD = "password";

  public static User unsavedUser() {
    return User.builder()
        .nickName("닉네임")
        .userRole(UserRole.PL)
        .accountId(USER_ACCOUNT_ID)
        .password(PASSWORD)
        .build();
  }
}
