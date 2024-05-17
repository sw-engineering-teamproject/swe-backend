package swe.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static swe.fixture.UserFixture.unsavedUser;
import static swe.user.domain.UserRole.TESTER;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import swe.support.ServiceTest;
import swe.user.User;
import swe.user.domain.UserRepository;
import swe.user.dto.UserRegisterRequest;

class UserServiceTest extends ServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JwtProvider jwtProvider;

  @Test
  void 유저를_회원가입하고_accessToken을_반환한다() {
    //given
    final UserRegisterRequest registerRequest
        = new UserRegisterRequest("hong", TESTER, "홍혁준", "password");
    final User expected = registerRequest.toUser();

    //when
    final String accessToken = userService.register(registerRequest);

    //then
    final Long userId = jwtProvider.parseMemberId(accessToken);
    final User savedUser = userRepository.readById(userId);

    assertThat(savedUser)
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(expected);
  }

  @Test
  void 유저가_로그인한다() {
    //given
    final User savedUser = userRepository.save(unsavedUser());

    //when
    final User loginUser = userService.login(savedUser.getAccountId(), savedUser.getPassword());

    //then
    assertThat(loginUser)
        .usingRecursiveComparison()
        .isEqualTo(savedUser);
  }
}