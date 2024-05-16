package jdbc.user.service;

import jdbc.user.dto.User;
import jdbc.user.repository.LoginRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {
    LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
    public int login(String id, String pw) {
        return loginRepository.login(id, makeHashcode(pw));
    }

    public boolean enroll(User user) {
        user.setPw(makeHashcode(user.getPw()));
        return loginRepository.enroll(user);
    }

    public boolean checkNicknameDuplicate(String nickname) {
        return loginRepository.checkNicknameDuplicate(nickname);
    }

    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        roles.add("PL");
        roles.add("dev");
        roles.add("tester");

        return roles;
    }
    public String makeHashcode(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(str.getBytes());

            StringBuilder hashHex = new StringBuilder();
            for (byte b : hashBytes) {
                hashHex.append(String.format("%02x", b));
            }

            return hashHex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
