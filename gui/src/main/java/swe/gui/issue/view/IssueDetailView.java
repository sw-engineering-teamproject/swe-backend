package swe.gui.issue.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import swe.gui.SessionStorage;
import swe.issue.application.IssueService;
import swe.issue.domain.Comment;
import swe.issue.domain.Issue;

public class IssueDetailView {
    private JScrollPane scrollPane;
    private IssueService issueService;

    public void settingView(JPanel issuePanel, JFrame frame) {
        this.issueService = SessionStorage.issueService;
        issuePanel.setLayout(new GridLayout(0, 1));
        Issue issue = issueService.findIssueDetail(SessionStorage.currentIssue.getId());
        //issuePanel.setBorder(BorderFactory.createTitledBorder(issue.getTitle()));
        JLabel titleLabel = new JLabel("title: " + issue.getTitle());
        JLabel reporterLabel = new JLabel("reporter: " + issue.getReporter().getNickname());
        JLabel contentLabel = new JLabel("<html><p style='width:200px;'>" + issue.getDescription() + "</p></html>");
        issuePanel.add(titleLabel);
        issuePanel.add(reporterLabel);
        issuePanel.add(contentLabel);

        // 댓글 입력 필드 및 버튼
        JTextField commentField = new JTextField();
        JButton commentButton = new JButton("Add Comment");

        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new GridLayout(0, 2));
        commentPanel.add(commentField);
        commentPanel.add(commentButton);

        scrollPane = new JScrollPane(issuePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        issuePanel.add(commentPanel);
        JPanel comments = new JPanel();

        // 댓글 모음
        comments.setLayout(new GridLayout(0, 3));
        issuePanel.add(comments);
        for(Comment comment : issue.getComments()){
            comments.add(new JLabel(comment.getContent()));
            comments.add(new JLabel(comment.getCommenter().getNickname()));
            comments.add(new JLabel(comment.getCreatedAt().toString()));
        }
        // 댓글 버튼 이벤트 핸들러
        commentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String comment = commentField.getText();
                if (!comment.isEmpty()) {
                    JLabel commentLabel = new JLabel(comment);
                    issueService.commentContent(SessionStorage.loginUser.getId(), SessionStorage.currentIssue.getId(), comment);
                    comments.add(commentLabel);
                    comments.add(new JLabel(SessionStorage.loginUser.getNickname()));
                    comments.add(new JLabel(LocalDateTime.now().toString()));
                    comments.revalidate();
                    comments.repaint();
                    commentField.setText("");
                } else {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Comment cannot be empty",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        frame.add(scrollPane);
    }
}
