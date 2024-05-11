package swe.core.acceptacne.user;

import static swe.core.acceptacne.support.AcceptanceSupport.post;

import org.junit.jupiter.api.Test;
import swe.core.acceptacne.support.AcceptanceTest;

public class UserAcceptanceTest extends AcceptanceTest {

  @Test
  void 유저를_생성한다() {
    final UserAddRequest userAddRequest = new UserAddRequest("id", "password");

    post("/users", userAddRequest);
  }
}
