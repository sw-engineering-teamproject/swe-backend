package swe.core.user;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class User {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String nickName;

  private String accountId;

  private String password;

  @Enumerated(EnumType.STRING)
  private UserAuthority userAuthority;

  @Builder
  public User(
      final String nickName, final String accountId,
      final String password, final UserAuthority userAuthority
  ) {
    this.nickName = nickName;
    this.accountId = accountId;
    this.password = password;
    this.userAuthority = userAuthority;
  }
}
