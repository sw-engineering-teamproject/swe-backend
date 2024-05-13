package swe.acceptacne.support;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class AcceptanceSupport {

  public static ExtractableResponse<Response> post(final String uri) {
    return RestAssured
        .given().log().ifValidationFails()
        .when().log().ifValidationFails()
        .post(uri)
        .then().statusCode(isHttpSuccess()).log().ifError()
        .extract();
  }

  public static ExtractableResponse<Response> post(final String uri, final Object body) {
    return RestAssured
        .given().log().ifValidationFails()
        .contentType(APPLICATION_JSON_VALUE)
        .body(body)
        .when().log().ifValidationFails()
        .post(uri)
        .then().statusCode(isHttpSuccess())
        .log().ifError()
        .extract();
  }

  public static ExtractableResponse<Response> post(final String uri, final String accessToken) {
    return RestAssured
        .given().log().ifValidationFails()
        .auth().preemptive().oauth2(accessToken)
        .when().log().ifValidationFails()
        .post(uri)
        .then().statusCode(isHttpSuccess()).log().ifError()
        .extract();
  }

  public static ExtractableResponse<Response> post(final String uri, final String accessToken,
      final String refreshToken) {
    return RestAssured
        .given().log().ifValidationFails()
        .auth().preemptive().oauth2(accessToken)
        .cookie("refreshToken", refreshToken)
        .when().log().ifValidationFails()
        .post(uri)
        .then().statusCode(isHttpSuccess()).log().ifError()
        .extract();
  }

  public static ExtractableResponse<Response> post(final String uri, final String accessToken,
      final Object body) {
    return RestAssured
        .given().log().ifValidationFails()
        .auth().preemptive().oauth2(accessToken)
        .contentType(APPLICATION_JSON_VALUE)
        .body(body)
        .when().log().ifValidationFails()
        .post(uri)
        .then().statusCode(isHttpSuccess()).log().ifError()
        .extract();
  }


  public static ExtractableResponse<Response> get(final String uri, final String accessToken) {
    return RestAssured
        .given().log().ifValidationFails()
        .auth().preemptive().oauth2(accessToken)
        .when().log().ifValidationFails()
        .get(uri)
        .then().statusCode(isHttpSuccess()).log().ifError()
        .extract();
  }

  public static ExtractableResponse<Response> patch(final String uri) {
    return RestAssured
        .given().log().ifValidationFails()
        .when().log().ifValidationFails()
        .patch(uri)
        .then().statusCode(isHttpSuccess()).log().ifError()
        .extract();
  }

  public static ExtractableResponse<Response> patch(final String uri, final String accessToken) {
    return RestAssured
        .given().log().ifValidationFails()
        .auth().preemptive().oauth2(accessToken)
        .when().log().ifValidationFails()
        .patch(uri)
        .then().statusCode(isHttpSuccess()).log().ifError()
        .extract();
  }

  public static ExtractableResponse<Response> patch(final String uri, final String accessToken,
      final Object body) {
    return RestAssured
        .given().log().ifValidationFails()
        .auth().preemptive().oauth2(accessToken)
        .contentType(APPLICATION_JSON_VALUE)
        .body(body)
        .when().log().ifValidationFails()
        .patch(uri)
        .then().statusCode(isHttpSuccess()).log().ifError()
        .extract();
  }

  private static Matcher<Integer> isHttpSuccess() {
    return Matchers.allOf(greaterThanOrEqualTo(200), lessThan(300));
  }
}
