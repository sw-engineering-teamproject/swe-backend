package jdbc.login;

import jdbc.user.dto.User;
import jdbc.user.service.LoginService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {
    LoginService loginService;
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    //로그인 api
    @GetMapping("/register")
    public int login(@RequestParam String id, @RequestParam String pw) {
        return loginService.login(id, pw);
    }

    //nickname 중복 여부 체크 api
    @GetMapping("/register/nickname")
    public boolean checkNicknameDuplicate(@RequestParam String nickname) {
        return loginService.checkNicknameDuplicate(nickname);
    }

    //역할 리스트 반환
    @GetMapping("/register/role")
    public List<String> getRoles() {
        return loginService.getRoles();
    }

    //회원가입 api
    @PostMapping("/user/enroll")
    public boolean userEnroll(@RequestBody User user) {
        return loginService.enroll(user);
    }
}
