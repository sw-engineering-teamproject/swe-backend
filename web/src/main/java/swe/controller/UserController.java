package swe.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swe.dto.user.DuplicateNicknameRequest;
import swe.dto.user.DuplicateNicknameResponse;
import swe.dto.user.LoginResponse;
import swe.dto.user.UserLoginRequest;
import swe.dto.user.UserResponse;
import swe.dto.user.UserRoleResponse;
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

  @GetMapping("/users")
  public ResponseEntity<List<UserResponse>> userList() {
    final var users = userService.findAllUsers();
    return ResponseEntity.ok(UserResponse.createList(users));
  }

  @PostMapping("/users/nickname/check")
  public ResponseEntity<DuplicateNicknameResponse> checkDuplicateNickName(
      @RequestBody final DuplicateNicknameRequest request
  ) {
    final Boolean isDuplicated = userService.checkDuplicateNickname(request.nickname());
    return ResponseEntity.ok(new DuplicateNicknameResponse(isDuplicated));
  }

  @GetMapping("/users/role")
  public ResponseEntity<UserRoleResponse> readAllUserRoles() {
    final var allUserRoles = userService.getAllUserRoles();
    return ResponseEntity.ok(UserRoleResponse.from(allUserRoles));
  }
}
