package swe;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import swe.gui.user.AuthPage;

@SpringBootApplication
@RequiredArgsConstructor
public class GuiApplication {

  public static void main(String[] args) {
    System.setProperty("spring.config.name", "application-core");
    ApplicationContext applicationContext = SpringApplication.run(GuiApplication.class, args);
    System.setProperty("java.awt.headless", "false");
    new AuthPage(applicationContext);
  }

}
