package swe.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import swe.user.domain.User;
import swe.user.exception.UserException;
import swe.user.exception.UserExceptionType;

class UserTest {

  @Nested
  class 유저의_패스워드가_동일한지_검증한다 {

    @Test
    void 유저의_패스워드가_동일한경우() {
      final String validatePassword = "validatePassword";

      final User user = User.builder()
          .password(validatePassword)
          .build();

      user.validateUserPassword(validatePassword);
    }

    @Test
    void 유저의_패스워드가_동일하지_않은_경우() {
      final String validatePassword = "validatePassword";
      final String unValidatePassword = "";

      final User user = User.builder()
          .password(validatePassword)
          .build();

      assertThatThrownBy(() -> user.validateUserPassword(unValidatePassword))
          .isInstanceOf(UserException.class)
          .hasMessage(UserExceptionType.USER_PASSWORD_NOT_EQUAL.getMessage());
    }
  }
}