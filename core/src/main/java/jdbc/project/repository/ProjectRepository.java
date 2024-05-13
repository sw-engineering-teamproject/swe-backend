package jdbc.project.repository;

import jdbc.project.dto.Project;
import jdbc.project.dto.ProjectList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepository {
    JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean projectEnroll(Project project) {
        String insertSql = "insert into project(name, reporter) values (?, ?);";
        try {
            jdbcTemplate.update(insertSql, project.getName(), project.getReporter());
        } catch (DataAccessException ex) {
            return false;
        }

        return true;
    }

    public List<ProjectList> getProjectList() {
        String getListSql = "select key, name, reporter from project;";
        List<ProjectList> list = jdbcTemplate.query(getListSql, (rs, rowNum) -> {
            return new ProjectList(rs.getInt("key"), rs.getString("name"), rs.getString("reporter"));
        });

        return list;
    }
}
