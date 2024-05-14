package swe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swe.dto.RegisterResponse;
import swe.user.application.UserService;
import swe.user.dto.UserRegisterRequest;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(@RequestBody final UserRegisterRequest request) {
    var accessToken = userService.register(request);
    return ResponseEntity.ok(new RegisterResponse(accessToken));
  }
}
