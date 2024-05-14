package swe.user.application;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private static final String TOKEN_ISSUER = "cau-swe";
  private static final int DURATION_DAY = 90;
  private static final String encodedSecretKey = "RQYjotA4VyWLZHmvtwMNzM7LTs1JIo3PNBmPKrPyxlw=";
  private static final String USER_ID_IDENTIFIER = "userId";

  public String createAccessTokenWith(final Long memberId) {
    final Date now = new Date();
    final Date expiration
        = new Date(now.getTime() + Duration.ofDays(DURATION_DAY).toMillis());
    final Map<String, Object> claims = Map.of(USER_ID_IDENTIFIER, memberId);

    return Jwts.builder()
        .issuer(TOKEN_ISSUER)
        .issuedAt(now)
        .expiration(expiration)
        .claims(claims)
        .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedSecretKey)))
        .compact();
  }

  public Long parseMemberId(final String jwt) {
    final Integer userId = (Integer) Jwts.parser()
        .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedSecretKey)))
        .build()
        .parseSignedClaims(jwt)
        .getPayload()
        .get(USER_ID_IDENTIFIER);
    return Long.valueOf(userId);
  }
}
