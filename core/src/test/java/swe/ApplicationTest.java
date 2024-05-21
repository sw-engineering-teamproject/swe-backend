package swe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.config.name=" +
    "application-core"
)
public class ApplicationTest {

  @Test
  void contextLoads() {
  }
}
