package swe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import swe.gui.user.AuthPage;
import swe.user.application.UserService;

@SpringBootApplication
public class GuiApplication {

  public static void main(String[] args) {
    new AuthPage();
    SpringApplication.run(GuiApplication.class, args);
  }

}
