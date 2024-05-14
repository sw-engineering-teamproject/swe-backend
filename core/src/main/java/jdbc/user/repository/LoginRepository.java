package jdbc.user.repository;

import jdbc.user.dto.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoginRepository {
    JdbcTemplate jdbcTemplate;

    public LoginRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public int login(String id, String pw) {
        String loginSql = "select key from user where id = ? and pw = ?;";
        List<Integer> key = jdbcTemplate.query(loginSql, (rs, rowNum) -> {
            return Integer.valueOf(rs.getInt("key"));
        }, id, pw);

        if (key.isEmpty())
            return 0;
        else
            return key.get(0);
    }

    public boolean checkNicknameDuplicate(String nickname) {
        String checkSql = "select nickname from user where nickname = ?;";
        List<String> nicks = jdbcTemplate.query(checkSql, (rs, rowNum) -> {
            return new String(rs.getString("nickname"));
        }, nickname);

        return nicks.isEmpty();
    }


    public boolean enroll(User user) {
        String enrollSql = "insert into user(nickname, id, pw, role) values(?, ?, ?, ?);";
        try {
            jdbcTemplate.update(enrollSql, user.getNickname(), user.getId(), user.getPw(), user.getRole());
        } catch(DataAccessException ex) {
            //만약 insert에 실패했을 경우
            return false;
        }

        return true;
    }
}

