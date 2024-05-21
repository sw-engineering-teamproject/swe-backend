package swe;

import javax.swing.JFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import swe.gui.issue.IssuePage;
import swe.gui.project.ProjectPage;
import swe.gui.user.AuthPage;
import swe.user.application.UserService;

@SpringBootApplication
@RequiredArgsConstructor
public class GuiApplication {

  public static void main(String[] args) {
    ApplicationContext applicationContext = SpringApplication.run(GuiApplication.class, args);
    System.setProperty("java.awt.headless", "false");
    new AuthPage(applicationContext);
  }

}
