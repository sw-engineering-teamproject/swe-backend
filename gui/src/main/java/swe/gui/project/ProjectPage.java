package swe.gui.project;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import swe.project.Project;

public class ProjectPage {
    public ProjectPage() {

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
        panel.add(title, gbc);

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
        updateResultsPanel(projects);
        JButton createBtn = new JButton();
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(createBtn, gbc);

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CreateProjectPage();
            }
        });
    }

    private void updateResultsPanel(JPanel projects) {
        projects.removeAll(); // 기존 결과를 지웁니다.

//        List<Project> results = projectService.getProjectList();
//
//        if (results.isEmpty()) {
//            JLabel noResultsLabel = new JLabel("No results found.");
//            projects.add(noResultsLabel);
//        } else {
//            for (Project project : results) {
//                JButton issueButton = new JButton(project.getName());
//                projectButton.setSize(1000, 100);
//                projectButton.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        new IssuePage
//                    }
//                });
//                projects.add(projectButton);
//            }
//        }

        projects.revalidate(); // UI를 업데이트합니다.
        projects.repaint();   // UI를 다시 그립니다.
    }

}
