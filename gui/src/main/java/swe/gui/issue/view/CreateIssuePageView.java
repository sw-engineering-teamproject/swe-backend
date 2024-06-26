package swe.gui.issue.view;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import swe.gui.SessionStorage;
import swe.gui.issue.IssuePage;
import swe.issue.application.IssueService;
import swe.issue.dto.IssueCreateRequest;

public class CreateIssuePageView {
    private IssueService issueService;

    public void settingView(JPanel panel, JFrame frame) {
        this.issueService = SessionStorage.issueService;
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Title");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(titleLabel, c);

        JTextField titleField = new JTextField(15);
        c.gridx = 1;
        panel.add(titleField, c);

        JLabel descriptionLabel = new JLabel("Description");
        c.gridx = 0;
        c.gridy = 1;
        panel.add(descriptionLabel, c);

        JTextArea descriptionArea = new JTextArea(4, 15);
        c.gridx = 1;
        descriptionArea.setLineWrap(true);
        panel.add(descriptionArea, c);

        JButton submitBtn = new JButton("Submit");
        c.gridy = 2;
        c.insets = new Insets(10, 0, 0, 0);
        panel.add(submitBtn, c);

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descriptionArea.getText();
                if(title.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        frame, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    IssueCreateRequest issueCreateRequest = new IssueCreateRequest(title, description, SessionStorage.currentProject.id());
                    issueService.createIssue(SessionStorage.loginUser.getId(), issueCreateRequest);
                    JOptionPane.showMessageDialog(
                        frame,
                        "이슈 생성 성공",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    frame.dispose();
                    new IssuePage();
                }
            }
        });
    }

}
