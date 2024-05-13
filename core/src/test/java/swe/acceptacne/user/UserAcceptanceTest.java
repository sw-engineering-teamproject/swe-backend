package swe.acceptacne.user;

import static swe.acceptacne.support.AcceptanceSupport.post;

import org.junit.jupiter.api.Test;
import swe.acceptacne.support.AcceptanceTest;

public class UserAcceptanceTest extends AcceptanceTest {

  @Test
  void 유저를_생성한다() {
    final UserAddRequest userAddRequest = new UserAddRequest("id", "password");

    post("/users", userAddRequest);
  }
}
