package swe.user.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class JwtProviderTest {

  private JwtProvider jwtProvider = new JwtProvider();

  @Test
  void 토큰을_발급한다() {
    //given
    final Long memberId = 10L;

    //when
    final String jwt = jwtProvider.createAccessTokenWith(memberId);

    //then
    final Long paredMemberId = jwtProvider.parseMemberId(jwt);
    assertThat(paredMemberId)
        .isEqualTo(memberId);
  }

  @Test
  void 토큰을_추출한다() {
    //given
    final Long memberId = 10L;
    final String jwt = jwtProvider.createAccessTokenWith(memberId);

    //when
    final Long paredMemberId = jwtProvider.parseMemberId(jwt);

    //then
    assertThat(paredMemberId)
        .isEqualTo(memberId);
  }
}
