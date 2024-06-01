package swe;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import swe.gui.SessionStorage;
import swe.gui.user.AuthPage;
import swe.issue.application.IssueService;
import swe.project.application.ProjectService;
import swe.user.application.UserService;
import swe.user.dto.UserRegisterRequest;

@SpringBootApplication
public class GuiApplication {

  public static void main(String[] args) {
    System.setProperty("spring.config.name", "application-core");
    ApplicationContext applicationContext = SpringApplication.run(GuiApplication.class, args);
    SessionStorage.userService = applicationContext.getBean(UserService.class);
    SessionStorage.projectService = applicationContext.getBean(ProjectService.class);
    SessionStorage.issueService = applicationContext.getBean(IssueService.class);
    //admin 생성
    UserRegisterRequest userRegisterRequest = new UserRegisterRequest("admin", "admin", "admin", "admin");
    SessionStorage.userService.register(userRegisterRequest);
    System.setProperty("java.awt.headless", "false");
    new AuthPage();
  }

}
