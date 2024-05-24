package swe.gui.project;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.springframework.context.ApplicationContext;
import swe.gui.SessionStorage;
import swe.gui.issue.IssuePage;
import swe.project.application.ProjectService;
import swe.project.dto.ProjectOverviewResponse;

public class ProjectPage {
    private final ProjectService projectService;
    private final ApplicationContext applicationContext;
    public ProjectPage(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.projectService = applicationContext.getBean(ProjectService.class);

        JFrame frame = new JFrame("Project Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel();
        frame.add(panel);

        projectForm(panel, frame);

        frame.setVisible(true);
    }

    private void projectForm(JPanel panel, JFrame frame) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel title = new JLabel("Project");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 3, 5, 30);
        panel.add(title, gbc);
        JButton createBtn = new JButton("new");
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(createBtn, gbc);

        gbc.insets = new Insets(0, 3, 0, 3);
        // 결과 표시 영역 (JPanel)
        JPanel projects = new JPanel();
        projects.setLayout(new BoxLayout(projects, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(projects, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        // 초기 결과 표시
        updateResultsPanel(projects, frame);
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CreateProjectPage(applicationContext);
            }
        });
    }

    private void updateResultsPanel(JPanel projects, JFrame frame) {
        projects.removeAll(); // 기존 결과를 지움
        List<ProjectOverviewResponse> results = projectService.readAllProject();
        GridBagConstraints gcb = new GridBagConstraints();
        gcb.fill = GridBagConstraints.HORIZONTAL;
        gcb.insets = new Insets(5, 5, 5, 5);

        if (results.isEmpty()) {
            JLabel noResultsLabel = new JLabel("No results found.");
            projects.add(noResultsLabel);
        } else {
            for (ProjectOverviewResponse project : results) {
                JButton projectButton = new JButton(project.title());
                gcb.fill = GridBagConstraints.HORIZONTAL;
                projectButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SessionStorage.currentProject = project;
                        frame.dispose();
                        new IssuePage(applicationContext);
                    }
                });
                projects.add(projectButton, gcb);
            }
        }

        projects.revalidate(); // UI를 업데이트.
        projects.repaint();   // UI를 다시 그림.
    }

}
