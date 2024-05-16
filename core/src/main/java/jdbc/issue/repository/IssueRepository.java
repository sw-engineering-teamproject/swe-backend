package jdbc.issue.repository;

import jdbc.issue.dto.Issue;
import jdbc.issue.dto.IssueEnroll;
import jdbc.issue.dto.IssueList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IssueRepository {
    JdbcTemplate jdbcTemplate;

    public IssueRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<IssueList> getIssueList() {
        String getListSql = "select key, title, reporter, assignee, status from issue;";
        List<IssueList> list = jdbcTemplate.query(getListSql, (rs, rowNum) -> {
            return new IssueList(rs.getInt("key"), rs.getString("title"), rs.getString("reporter"),
                    rs.getString("assignee"), rs.getString("status"));
        });

        return list;
    }

    public List<IssueList> getIssueBySearch(String criterion, String value) {
        String sql = "select key, title, reporter, assignee, status from issue where " + criterion + " = ?;";
        List<IssueList> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new IssueList(rs.getInt("key"), rs.getString("title"), rs.getString("reporter"),
                    rs.getString("assignee"), rs.getString("status"));
        }, value);

        return list;
    }

    public boolean issueEnroll(IssueEnroll issue) {
        String getNicknameSql = "select nickname from user where key = ?;";

        try {
            List<String> nickname = jdbcTemplate.query(getNicknameSql, (rs, rowNum) -> {
                return new String(rs.getString("nickname"));
            }, issue.getUserKey());

            String insertSql = "insert into issue(title, description, reporter, priority, status) values " +
                    "(?, ?, ?, ?, ?);";
            jdbcTemplate.update(insertSql, issue.getTitle(), issue.getDescription(), nickname.get(0), "major", "new");
        } catch(DataAccessException ex) {
            return false;
        }

        return true;
    }

    public Issue getIssueInfo(int key) {
        String getInfoSql = "select title, description, reporter, time, fixer, assignee, priority, status from issue where" +
                " key = ?;";
        List<Issue> issue = jdbcTemplate.query(getInfoSql, (rs, rowNum) -> {
            return new Issue(rs.getString("title"), rs.getString("description"), rs.getString("reporter"),
                    rs.getString("time"), rs.getString("fixer"), rs.getString("assignee"),
                    rs.getString("priority"), rs.getString("status"));
        }, key);

        return issue.get(0);
    }

    public boolean setPriority(int issueKey, String priority) {
        String setPrioritySql = "update issue set priority = ? where key = ?;";
        try {
            jdbcTemplate.update(setPrioritySql, priority, issueKey);
        } catch (DataAccessException ex) {
            return false;
        }

        return true;
    }


    @AllArgsConstructor
    @Getter
    @Setter
    class RoleAndStatus {
        String nickname;
        String role;
        String status;
    }
    public boolean setStatus(int issueKey, int userKey, String status) {
        try {
            //user의 이름, 역할, 그리고 이슈의 상태를 반환 받음.
            RoleAndStatus rs = getUserRoleAndIssueStatus(issueKey, userKey, status);
            //status에 따라 맞춰서 처리함.
            if (!setStatus(issueKey, rs, status)) {
                return false;
            }
        } catch (DataAccessException ex) {
            return false;
        }
        return true;
    }

    public RoleAndStatus getUserRoleAndIssueStatus(int issueKey, int userKey, String status) throws DataAccessException{
        String getRoleSql = "select nickname, role from user where key = ?;";
        String getStatusSql = "select status from issue where key = ?;";
        List<RoleAndStatus> userResult = jdbcTemplate.query(getRoleSql, (rs, rowNum) -> {
            return new RoleAndStatus(rs.getString("nickname"), rs.getString("role"), null);
        }, userKey);
        List<String> statusResult = jdbcTemplate.query(getStatusSql, (rs, rowNum) -> {
            return new String(rs.getString("status"));
        }, issueKey);

        RoleAndStatus rs = userResult.get(0);
        rs.setStatus(statusResult.get(0));
        return rs;
    }

    public boolean setStatus(int issueKey, RoleAndStatus rs, String status) throws  DataAccessException{
        //assigned-이전 상태가 new인지, user가 dev인지 확인필요, user를 assignee로 등록해야함.
        if (status.equals("assigned") && rs.getStatus().equals("new") && rs.getRole().equals("dev")) {
            String setAssignSql = "update issue set assignee = ? and status = ? where key = ?;";
            jdbcTemplate.update(setAssignSql, rs.getNickname(), "assigned", issueKey);
        } //fixed-이전 상태가 assigned인지, user가 dev인지 확인필요, user를 fixer에 등록해야함.
        else if(status.equals("fixed") && rs.getStatus().equals("assigned") && rs.getRole().equals("dev")) {
            String setFixedSql = "update issue set fixer = ? and status = ? where key = ?;";
            jdbcTemplate.update(setFixedSql, rs.getNickname(), "fixed", issueKey);
        } //resolved-이전 상태가 fixed인지, user가 tester인지 확인필요.
        else if(status.equals("resolved") && rs.getStatus().equals("fixed") && rs.getRole().equals("tester")) {
            String setResolvedSql = "update issue set status = ? where key = ?;";
            jdbcTemplate.update(setResolvedSql, "resolved", issueKey);
        } //closed-이전 상태가 resolved인지, user가 PL인지 확인필요.
        else if(status.equals("closed") && rs.getStatus().equals("resolved") && rs.getRole().equals("PL")) {
            String setClosedSql = "update issue set status = ? where key = ?;";
            jdbcTemplate.update(setClosedSql, "closed", issueKey);
        } else {
            return false;
        }

        return true;
    }
}
