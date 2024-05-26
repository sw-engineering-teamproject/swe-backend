package swe.gui.issue.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.springframework.context.ApplicationContext;
import swe.gui.SessionStorage;
import swe.gui.issue.IssueDetail;
import swe.issue.application.IssueService;

public class IssueDetailView {
    private IssueService issueService;
    public void settingView(JPanel panel, JFrame frame){
        this.issueService = SessionStorage.issueService;
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Title");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(titleLabel, c);


        JLabel descriptionLabel = new JLabel("Description");
        c.gridx = 0;
        c.gridy = 1;
        panel.add(descriptionLabel, c);

        JLabel commentLabel = new JLabel("Comment");
        c.gridx = 0;
        c.gridy = 2;
        panel.add(commentLabel, c);

        JTextArea commentArea = new JTextArea(4, 15);
        c.gridy = 3;
        panel.add(commentArea, c);
        JButton commentBtn = new JButton("등록");
        c.gridx = 1;
        panel.add(commentBtn, c);


        commentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String comment = commentArea.getText();
                if(comment.isEmpty()){
                    JOptionPane.showMessageDialog(
                        frame, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    issueService.commentContent(SessionStorage.loginUser.getId(), SessionStorage.currentIssue.getId(), comment);
                    frame.dispose();
                    new IssueDetail();
                }
            }
        });
    }

}
