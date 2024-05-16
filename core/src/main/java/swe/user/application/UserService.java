package swe.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.user.User;
import swe.user.domain.UserRepository;
import swe.user.dto.UserRegisterRequest;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;

  @Transactional
  public String register(final UserRegisterRequest request) {
    final User user = request.toUser();
    final User savedUser = userRepository.save(user);

    return jwtProvider.createAccessTokenWith(savedUser.getId());
  }

  @Transactional(readOnly = true)
  public void login() {

  }
}
