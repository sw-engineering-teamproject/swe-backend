package swe.dto.user;

import lombok.Builder;
import swe.user.domain.User;

@Builder
public record LoginResponse(String token, String nickname, Long id, String role) {

  public static LoginResponse of(final String token, final User user) {
    return LoginResponse.builder()
        .token(token)
        .nickname(user.getNickname())
        .id(user.getId())
        .role(user.getUserRole().getName())
        .build();
  }
}
