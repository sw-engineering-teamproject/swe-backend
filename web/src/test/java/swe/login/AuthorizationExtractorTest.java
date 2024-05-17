package swe.login;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

class AuthorizationExtractorTest {

  @Test
  void 정상적으로_파싱하는지_확인() {
    //given
    final String token = "Bearer accessToken";

    final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    when(mockRequest.getHeader(AUTHORIZATION)).thenReturn(token);

    //when
    final String actual = AuthorizationExtractor.extract(mockRequest);
    final String expected = "accessToken";

    //then
    assertThat(actual)
        .isEqualTo(expected);
  }
}
