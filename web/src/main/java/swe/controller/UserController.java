package swe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swe.dto.LoginResponse;
import swe.dto.UserLoginRequest;
import swe.user.application.JwtProvider;
import swe.user.application.UserService;
import swe.user.dto.UserRegisterRequest;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final JwtProvider jwtProvider;

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody final UserRegisterRequest request) {
    userService.register(request);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody final UserLoginRequest request) {
    var user = userService.login(request.accountId(), request.password());
    final String accessToken = jwtProvider.createAccessTokenWith(user.getId());
    return ResponseEntity.ok(new LoginResponse(accessToken));
  }
}
