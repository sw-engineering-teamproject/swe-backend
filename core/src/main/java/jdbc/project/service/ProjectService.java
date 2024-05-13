package jdbc.project.service;

import jdbc.project.dto.Project;
import jdbc.project.dto.ProjectList;
import jdbc.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public boolean projectEnroll(Project project) {
        return projectRepository.projectEnroll(project);
    }

    public List<ProjectList> getProjectList() {
        return projectRepository.getProjectList();
    }
}
