package swe.user;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swe.user.domain.UserRole;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "user_")
@Getter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String accountId;

  private String password;

  private String nickName;

  @Enumerated(value = EnumType.STRING)
  private UserRole userRole;

  @Builder
  public User(
      final String accountId, final String password, final String nickName, final UserRole userRole
  ) {
    this.accountId = accountId;
    this.password = password;
    this.nickName = nickName;
    this.userRole = userRole;
  }
}
