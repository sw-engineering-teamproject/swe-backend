package swe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import swe.gui.issue.CreateIssuePage;
import swe.gui.issue.IssuePage;
import swe.gui.project.ProjectPage;
import swe.gui.user.AuthPage;

@SpringBootApplication
public class GuiApplication {

  public static void main(String[] args) {
    new AuthPage();
    SpringApplication.run(GuiApplication.class, args);
  }

}
