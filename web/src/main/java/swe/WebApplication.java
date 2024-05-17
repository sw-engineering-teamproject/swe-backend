package swe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplication {

  public static void main(String[] args) {
    System.setProperty("spring.config.name", "application-core,application-web");
    SpringApplication.run(WebApplication.class, args);
  }

}
