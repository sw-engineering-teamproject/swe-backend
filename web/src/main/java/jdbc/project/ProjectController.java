package jdbc.project;

import jdbc.project.dto.Project;
import jdbc.project.dto.ProjectList;
import jdbc.project.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {
    ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    //프로젝트 생성 api
    @PostMapping("/project/enroll")
    public boolean projectEnroll(@RequestBody Project project) {
        return projectService.projectEnroll(project);
    }

    //프로젝트 목록 api
    @GetMapping("/project/list")
    public List<ProjectList> getProjectList() {
        return projectService.getProjectList();
    }
}
