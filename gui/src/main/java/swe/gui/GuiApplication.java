package swe.gui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import swe.gui.user.AuthPage;

@SpringBootApplication
public class GuiApplication {

  public static void main(String[] args) {
    new AuthPage();
    SpringApplication.run(GuiApplication.class, args);
  }

}
