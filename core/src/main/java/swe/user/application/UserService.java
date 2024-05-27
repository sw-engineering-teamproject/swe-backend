package swe.user.application;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.user.domain.User;
import swe.user.domain.UserRepository;
import swe.user.domain.UserRole;
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

  @Transactional(readOnly = true)
  public Boolean checkDuplicateNickname(final String nickName) {
    return userRepository.existsByNickname(nickName);
  }

  @Transactional(readOnly = true)
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public List<UserRole> getAllUserRoles() {
    return Arrays.stream(UserRole.values())
        .toList();
  }
}
