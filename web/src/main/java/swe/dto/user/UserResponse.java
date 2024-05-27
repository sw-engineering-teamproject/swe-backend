package swe.dto.user;

import java.util.List;
import swe.user.domain.User;

public record UserResponse(Long id, String name) {

  public static UserResponse from(final User user) {
    return new UserResponse(user.getId(), user.getNickname());
  }

  public static List<UserResponse> createList(final List<User> users) {
    return users.stream()
        .map(UserResponse::from)
        .toList();
  }
}
