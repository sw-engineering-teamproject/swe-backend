package swe.gui.issue;

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
import org.springframework.context.ApplicationContext;
import swe.issue.application.IssueService;
import swe.issue.domain.Issue;
import swe.issue.dto.IssueCreateRequest;

public class CreateIssuePage {
    private final ApplicationContext applicationContext;

    private final IssueService issueService;
    public CreateIssuePage(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.issueService = applicationContext.getBean(IssueService.class);
        JFrame frame = new JFrame("New Issue");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel();
        frame.add(panel);
        setupForm(panel, frame);
        frame.setVisible(true);
    }

    private void setupForm(JPanel panel, JFrame frame) {
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
                    IssueCreateRequest issueCreateRequest = new IssueCreateRequest(title, description, 1L);
                    issueService.createIssue(1L, issueCreateRequest);
                    JOptionPane.showMessageDialog(
                        frame,
                        "Issue created successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    frame.dispose();
                    new IssuePage(applicationContext);
                }
            }
        });
    }
}
