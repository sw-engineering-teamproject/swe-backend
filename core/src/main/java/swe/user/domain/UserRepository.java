package swe.user.domain;

import static swe.user.exception.UserExceptionType.USER_NOT_FOUND;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import swe.user.exception.UserException;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByAccountId(final String accountId);

  Optional<User> findByNickname(final String nickname);

  boolean existsByNickname(final String nickName);

  default User readById(final Long userId) {
    return findById(userId).orElseThrow(() -> new UserException(USER_NOT_FOUND));
  }

  default User readByAccountId(final String accountId) {
    return findByAccountId(accountId).orElseThrow(() -> new UserException(USER_NOT_FOUND));
  }

  default User readByNickname(final String nickname) {
    return findByNickname(nickname).orElseThrow(() -> new UserException(USER_NOT_FOUND));
  }
}
