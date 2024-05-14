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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import swe.project.Project;

public class CreateProjectPage {

    public CreateProjectPage() {
        // 폼 구성
        JFrame frame = new JFrame("New Project");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

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
                    // Issue 저장
                    //Project project = new Project(title, reporter);

                    //projectService.projectEnroll(project);
                    JOptionPane.showMessageDialog(
                        frame,
                        "Issue created successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    frame.dispose(); // 폼 닫기
                    new ProjectPage();
                }
            }
        });
    }
}
