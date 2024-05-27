package swe.user.domain;

import static lombok.AccessLevel.PROTECTED;
import static swe.user.exception.UserExceptionType.USER_PASSWORD_NOT_EQUAL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import swe.user.exception.UserException;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "user_")
@Getter
@ToString
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  @NotBlank
  private String accountId;

  @Column(nullable = false)
  @NotBlank
  private String password;

  @Column(nullable = false)
  @NotBlank
  private String nickname;

  @Enumerated(value = EnumType.STRING)
  @NotNull
  private UserRole userRole;

  public User(
      final Long id, final String accountId, final String password, final String nickname,
      final UserRole userRole
  ) {
    this.id = id;
    this.accountId = accountId;
    this.password = password;
    this.nickname = nickname;
    this.userRole = userRole;
  }

  @Builder
  public User(
      final String accountId, final String password, final String nickname, final UserRole userRole
  ) {
    this.accountId = accountId;
    this.password = password;
    this.nickname = nickname;
    this.userRole = userRole;
  }

  public void validateUserPassword(final String password) {
    if (!this.password.equals(password)) {
      throw new UserException(USER_PASSWORD_NOT_EQUAL);
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
