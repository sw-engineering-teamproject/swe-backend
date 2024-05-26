package swe.gui.issue.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
        issuePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Issue issue = issueService.findIssueDetail(SessionStorage.currentIssue.getId());
        //issuePanel.setBorder(BorderFactory.createTitledBorder(issue.getTitle()));
        gbc.gridy = 0;
        gbc.gridx = 0;
        JLabel titleLabel = new JLabel("title: " + issue.getTitle());
        issuePanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        JLabel reporterLabel = new JLabel("reporter: " + issue.getReporter().getNickname());
        issuePanel.add(reporterLabel, gbc);
        gbc.gridx = 2;
        JLabel asigneeLabel = new JLabel("assignee");
        issuePanel.add(asigneeLabel, gbc);

        gbc.gridx = 3;
        String recommendAssignee[] = {"api"};
        JComboBox<String> assignee = new JComboBox<>(recommendAssignee);
        assignee.setPreferredSize(new Dimension(150, 25));
        issuePanel.add(assignee, gbc);
        gbc.gridx = 0;

        gbc.gridy = 2;
        JLabel contentLabel = new JLabel(issue.getDescription());
        issuePanel.add(contentLabel, gbc);

        JLabel statusLabel = new JLabel("status");
        gbc.gridx = 2;
        issuePanel.add(statusLabel, gbc);
        gbc.gridx = 3;
        String statusList[] = {"new"};
        JComboBox<String> status = new JComboBox<>(statusList);
        status.setPreferredSize(new Dimension(150, 25));
        issuePanel.add(status, gbc);
        gbc.gridx = 0;





        gbc.gridy = 0;
        gbc.gridx = 2;
        JButton fixedBtn = new JButton("수정");
        issuePanel.add(fixedBtn, gbc);

        ///
        JTextArea contentFix = new JTextArea(contentLabel.getText(), 5, 15);
        ///

        // 댓글 입력 필드 및 버튼
        JTextField commentField = new JTextField(10);
        JButton commentButton = new JButton("Add Comment");
        gbc.gridy = 3;
        gbc.gridx = 0;
        issuePanel.add(commentField, gbc);
        gbc.gridx = 1;
        issuePanel.add(commentButton, gbc);

        JLabel rankLabel = new JLabel("Rank");
        gbc.gridx = 2;
        issuePanel.add(rankLabel, gbc);
        gbc.gridx = 3;
        String rankList[] = {"rank"};
        JComboBox<String> rank = new JComboBox<>(rankList);
        rank.setPreferredSize(new Dimension(150, 25));
        issuePanel.add(rank, gbc);
        gbc.gridx = 0;

        gbc.gridy = 4;
        JLabel fixerLabel = new JLabel("Fixer");
        gbc.gridx = 2;
        issuePanel.add(fixerLabel, gbc);
        gbc.gridx = 3;
        String fixerList[] = {"fixer"};
        JComboBox<String> fixer = new JComboBox<>(fixerList);
        fixer.setPreferredSize(new Dimension(150, 25));
        issuePanel.add(fixer, gbc);


        scrollPane = new JScrollPane(issuePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel comments = new JPanel();

        // 댓글 모음
        comments.setLayout(new GridLayout(0, 3));
        gbc.gridx = 0;
        gbc.gridy = 4;
        issuePanel.add(comments, gbc);
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
        fixedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(fixedBtn.getText(), "수정")) {
                    issuePanel.remove(contentLabel);
                    fixedBtn.setText("수정 완료");
                    gbc.gridy = 2;
                    gbc.gridx = 0;
                    issuePanel.add(contentFix, gbc);
                    issuePanel.revalidate();
                    issuePanel.repaint();
                }
                else {
                    issuePanel.remove(contentFix);
                    fixedBtn.setText("수정");
                    gbc.gridy = 2;
                    gbc.gridx = 0;
                    contentLabel.setText(contentFix.getText());
                    issueService.updateDescription(SessionStorage.loginUser.getId(), contentFix.getText());
                    issuePanel.add(contentLabel, gbc);
                    issuePanel.revalidate();
                    issuePanel.repaint();
                }
            }
        });
        frame.add(scrollPane);
    }
}
