package swe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {

  public static void main(String[] args) {
    System.setProperty("spring.config.name", "application-core");
    SpringApplication.run(CoreApplication.class, args);
  }

}
