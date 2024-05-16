package swe.user.domain;

import static swe.user.exception.UserExceptionType.USER_NOT_FOUND;

import org.springframework.data.jpa.repository.JpaRepository;
import swe.user.User;
import swe.user.exception.UserException;

public interface UserRepository extends JpaRepository<User, Long> {

  default User readBy(final Long userId) {
    return findById(userId).orElseThrow(() -> new UserException(USER_NOT_FOUND));
  }
}
