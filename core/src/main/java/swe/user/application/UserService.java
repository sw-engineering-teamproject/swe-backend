package swe.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.user.domain.User;
import swe.user.domain.UserRepository;
import swe.user.dto.UserRegisterRequest;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public void register(final UserRegisterRequest request) {
    final User user = request.toUser();
    userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public User login(final String accountId, final String password) {
    final User user = userRepository.readByAccountId(accountId);
    user.validateUserPassword(password);
    return user;
  }
}
