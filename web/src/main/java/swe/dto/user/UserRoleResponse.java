package swe.dto.user;

import java.util.List;
import swe.user.domain.UserRole;

public record UserRoleResponse(List<String> userRoleNames) {

  public static UserRoleResponse from(final List<UserRole> allUserRoles) {
    final List<String> userRoles = allUserRoles.stream()
        .map(UserRole::getName)
        .toList();

    return new UserRoleResponse(userRoles);
  }
}
