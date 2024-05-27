package swe.dto.user;

public record LoginResponse(String token, String nickname, Long id) {

}
