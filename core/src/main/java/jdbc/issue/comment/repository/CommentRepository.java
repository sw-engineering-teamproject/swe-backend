package jdbc.issue.comment.repository;

import jdbc.issue.comment.dto.CommentList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {
    JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean enrollComment(int issueKey, int userKey, String comment) {
        String getNicknameSql = "select nickname from user where key = ?;";
        String enrollSql = "insert into comment(issue_key, commenter, content) values(?, ?, ?);";
        try {
            List<String> nickname = jdbcTemplate.query(getNicknameSql, (rs, rowNum) -> {
                return new String(rs.getString("nickname"));
            }, userKey);
            jdbcTemplate.update(enrollSql, issueKey, nickname.get(0), comment);
        } catch (DataAccessException ex) {
            return false;
        }

        return true;
    }

    public List<CommentList> getComments(int issueKey) {
        String getCommentsSql = "select commenter, content, time from comment where issue_key = ?;";
        List<CommentList> commentLists = jdbcTemplate.query(getCommentsSql, (rs, rowNum) -> {
            return new CommentList(rs.getString("commenter"), rs.getString("content"), rs.getString("time"));
        }, issueKey);

        return commentLists;
    }
}
