package jdbc.login;

import jdbc.user.dto.User;
import jdbc.user.service.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    LoginService loginService;
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    //로그인 api
    @GetMapping("/user/login")
    public int login(@RequestParam String id, @RequestParam String pw) {
        return loginService.login(id, pw);
    }

    //회원가입 api
    @PostMapping("/user/enroll")
    public boolean userEnroll(@RequestBody User user) {
        return loginService.enroll(user);
    }
}
