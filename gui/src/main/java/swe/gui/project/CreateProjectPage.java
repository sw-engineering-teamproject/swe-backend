package swe.gui.project;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.context.ApplicationContext;
import swe.project.application.ProjectService;
import swe.project.domain.Project;

public class CreateProjectPage {
    private final ApplicationContext applicationContext;
    private final ProjectService projectService;

    public CreateProjectPage(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.projectService = applicationContext.getBean(ProjectService.class);
        // 폼 구성
        JFrame frame = new JFrame("New Project");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        frame.add(panel);

        setupForm(panel, frame);

        frame.setVisible(true);
    }

    private void setupForm(JPanel panel, JFrame frame) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // 제목 필드
        JLabel titleLabel = new JLabel("Title");
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 5, 5);
        panel.add(titleLabel, c);

        JTextField titleField = new JTextField(15);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(titleField, c);

        // reporter 필드
        JLabel reporterLabel = new JLabel("Content");
        c.gridx = 0;
        c.gridy = 2;
        panel.add(reporterLabel, c);

        JTextField reporterField = new JTextField(15);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(reporterField, c);

        // 제출 버튼
        JButton submitButton = new JButton("Submit");
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(10, 0, 0, 0);
        panel.add(submitButton, c);

        // 제출 버튼 이벤트 핸들러
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String reporter = reporterField.getText();

                if (title.isEmpty() || reporter.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "All fields are required",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    //수정 예정
                    projectService.createProject(title, 1L);
                    JOptionPane.showMessageDialog(
                        frame,
                        "Project created successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    frame.dispose(); // 폼 닫기
                    new ProjectPage(applicationContext);
                }
            }
        });
    }
}